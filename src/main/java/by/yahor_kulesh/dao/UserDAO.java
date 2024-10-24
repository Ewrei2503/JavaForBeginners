package by.yahor_kulesh.dao;

import by.yahor_kulesh.config.ConnectionConfig;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.model.users.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.UUID;

public class UserDAO{

    private ResultSet rs = null;

    public void insert(User user){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("INSERT INTO usr(id,name,creation_date,role) VALUES (?,?,?,CAST(? AS role_type))")
        ){
            prepareUserForStatement(user, stat);
            stat.execute();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public User getById(UUID id){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("SELECT * FROM usr where id=?")
        ) {
            stat.setObject(1, id);
            rs = stat.executeQuery();
            User user;
            if(rs.next()){
                if(rs.getString(4).equals("Client")){
                    user = new Client();
                } else{
                    user = new Admin();
                }
            } else return null;
            user.setId(UUID.fromString(rs.getString(1)));
            user.setName(rs.getString(2));
            user.setCreationTime(rs.getTimestamp(3).toLocalDateTime().atZone(ZoneId.systemDefault()));
            return user;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void update(User user){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("UPDATE usr SET id=?,name=?,creation_date=?,role=CAST(? AS role_type) WHERE id=?")
        ){
            prepareUserForStatement(user, stat);
            stat.setObject(5,user.getId());
            stat.executeUpdate();
        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void deleteById(UUID id){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("DELETE FROM usr WHERE id=?")
        ){
            if(id==null) throw new SQLException("Cannot delete: id is null");
            stat.setObject(1, id);
            stat.execute();
        } catch(SQLException e) {
            System.err.println(e.getMessage());
        }
    }


    private static void prepareUserForStatement(User user, PreparedStatement stat) throws SQLException {
        stat.setObject(1, user.getId());
        stat.setString(2, user.getName());
        stat.setTimestamp(3, Timestamp.valueOf(user.getCreationTime().toLocalDateTime()));
        stat.setObject(4, user.getRole());
    }
}
