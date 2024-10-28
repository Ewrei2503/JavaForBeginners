package by.yahor_kulesh.dao;

import by.yahor_kulesh.config.ConnectionConfig;
import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.entity.enums.TicketType;
import by.yahor_kulesh.utils.ObjectArray;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

@Component
public class TicketDAO{

    private final ConnectionConfig connectionConfig;

    private ResultSet rs = null;

    public TicketDAO(ConnectionConfig connectionConfig) {
        this.connectionConfig = connectionConfig;
    }

    public void insert(TicketEntity ticket){
        try(
                Connection con = connectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("INSERT INTO ticket(id,user_id,ticket_type,creation_date) VALUES (?,?,?,?)")
        ){
            prepareTicketForStatement(ticket, stat);
            stat.execute();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public TicketEntity getById(UUID id) {
        try(
                Connection con = connectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("SELECT * FROM ticket where id=?")
        ) {
            stat.setObject(1, id);
            rs = stat.executeQuery();
            if(rs.next()){
                return getTicketFromDB();
            }else return null;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public ObjectArray getByUserId(UUID id) {
        ObjectArray tickets = new ObjectArray();
        try(
                Connection con = connectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("SELECT * FROM ticket where user_id=?")
        ) {
            stat.setObject(1, id);
            rs = stat.executeQuery();
            while(rs.next()) {
                tickets.add(getTicketFromDB());
            }
            return tickets;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void update(TicketEntity ticket){
        try(
                Connection con = connectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("UPDATE ticket SET id=?,user_id=?,ticket_type=?,creation_date=? WHERE id=?")
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
                Connection con = connectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("DELETE FROM ticket WHERE id=?")
        ){
            stat.setObject(1, id);
            stat.execute();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void prepareTicketForStatement(TicketEntity ticket, PreparedStatement stat) throws SQLException {
        stat.setObject(1,ticket.getId());
        stat.setObject(2,ticket.getUser()==null?null:ticket.getUser().getId());
        stat.setObject(3,ticket.getType(), Types.OTHER);
        stat.setTimestamp(4, ticket.getCreationTime());
    }

    private TicketEntity getTicketFromDB() throws SQLException {
        TicketEntity ticket = new TicketEntity();
        ticket.setId(UUID.fromString(rs.getString(1)));
        ticket.setUser(new UserEntity(rs.getString(2) == null?null:UUID.fromString(rs.getString(2))));
        ticket.setType(TicketType.valueOf(rs.getString(3)));
        ticket.setCreationTime(rs.getTimestamp(4));
        return ticket;
    }
}
