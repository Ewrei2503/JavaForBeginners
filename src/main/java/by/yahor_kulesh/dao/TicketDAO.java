package by.yahor_kulesh.dao;

import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.utils.ObjectArray;

import java.sql.*;
import java.time.ZoneId;
import java.util.UUID;

public class TicketDAO{
    private ResultSet rs = null;

    public void insert(Ticket ticket){
        try(
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db","postgres","postgres");
                PreparedStatement stat = con.prepareStatement("INSERT INTO ticket(id,user_id,ticket_type,creation_date) VALUES (?,?,CAST(? AS ticket_type),?)")
        ){
            prepareTicketForStatement(ticket, stat);
            stat.execute();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public Ticket getById(UUID id) {
        try{
            try(
                    Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db", "postgres", "postgres");
                    PreparedStatement stat = con.prepareStatement("SELECT * FROM ticket where id=?")
            ) {
                stat.setObject(1, id);
                rs = stat.executeQuery();
                if(rs.next()){
                    return getTicketFromDB();
                }else return null;
            } finally {
                closeResultSet();
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ObjectArray getByUserId(UUID id) {
        ObjectArray tickets = new ObjectArray();
        try{
            try(
                    Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db", "postgres", "postgres");
                    PreparedStatement stat = con.prepareStatement("SELECT * FROM ticket where user_id=?")
            ) {
                stat.setObject(1, id);
                rs = stat.executeQuery();
                while(rs.next()) {
                    tickets.add(getTicketFromDB());
                }
                return tickets;
            } finally {
                closeResultSet();
            }
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void update(Ticket ticket){
        try(
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db","postgres","postgres");
                PreparedStatement stat = con.prepareStatement("UPDATE ticket SET id=?,user_id=?,ticket_type=CAST(? AS ticket_type),creation_date=? WHERE id=?")
        ){
            prepareTicketForStatement(ticket, stat);
            stat.setObject(5,ticket.getId());
            stat.executeUpdate();
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void deleteById(UUID id){
        try(
                Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/my_ticket_service_db","postgres","postgres");
                PreparedStatement stat = con.prepareStatement("DELETE FROM ticket WHERE id=?")
        ){
            stat.setObject(1, id);
            stat.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void prepareTicketForStatement(Ticket ticket, PreparedStatement stat) throws SQLException {
        stat.setObject(1,ticket.getId());
        stat.setObject(2,ticket.getUserId());
        stat.setObject(3,ticket instanceof ConcertTicket ? "concert": (ticket instanceof BusTicket ?"bus":"not defined"));
        stat.setTimestamp(4, Timestamp.valueOf(ticket.getCreationTime().toLocalDateTime()));
    }

    private Ticket getTicketFromDB() throws SQLException {
        Ticket ticket;
        if(rs.getString(3).equals("concert")) {
            ticket = new ConcertTicket();
        } else if(rs.getString(3).equals("bus")) {
            ticket = new BusTicket();
        } else ticket = new Ticket();
        ticket.setId((UUID) rs.getObject(1));
        ticket.setUserId((UUID) rs.getObject(2));
        ticket.setCreationTime(rs.getTimestamp(4).toLocalDateTime().atZone(ZoneId.systemDefault()));
        return ticket;
    }

    private void closeResultSet() throws SQLException {
        if(rs != null) {
            rs.close();
        }
    }
}
