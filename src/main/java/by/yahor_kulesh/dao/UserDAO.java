package by.yahor_kulesh.dao;

import by.yahor_kulesh.config.ConnectionConfig;
import by.yahor_kulesh.entity.UserEntity;
import by.yahor_kulesh.entity.enums.Role;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.UUID;

public class UserDAO{

    public void insert(UserEntity user){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("INSERT INTO usr(id,name,creation_date,role) VALUES (?,?,?,?)")
        ){
            prepareUserForStatement(user, stat);
            stat.execute();
        }catch(SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public UserEntity getById(UUID id){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("SELECT * FROM usr where id=?")
        ) {
            stat.setObject(1, id);
            ResultSet rs = stat.executeQuery();
            if(rs.next()) {
                UserEntity user = new UserEntity();
                user.setId(UUID.fromString(rs.getString(1)));
                user.setName(rs.getString(2));
                user.setCreationTime(rs.getTimestamp(3));
                user.setRole(Role.valueOf(rs.getString(4)));
                return user;
            } else return null;
        } catch(SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public void update(UserEntity user){
        try(
                Connection con = ConnectionConfig.getConnection();
                PreparedStatement stat = con.prepareStatement("UPDATE usr SET id=?,name=?,creation_date=?,role=? WHERE id=?")
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


    private static void prepareUserForStatement(UserEntity user, PreparedStatement stat) throws SQLException {
        stat.setObject(1, user.getId());
        stat.setString(2, user.getName());
        stat.setTimestamp(3, user.getCreationTime());
        stat.setObject(4, user.getRole(), Types.OTHER);
    }
}
