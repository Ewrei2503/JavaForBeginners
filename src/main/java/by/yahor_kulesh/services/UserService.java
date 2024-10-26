package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.UserDAO;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.users.User;

import java.util.UUID;

public class UserService extends Data {
    private static final UserDAO userDAO = new UserDAO();


    public static void insertOrUpdateUser(User user) {
        if(user==null || user.getId()==null) {
            System.err.println("User or user's ID cannot be null");
            return;
        }if(userDAO.getById(user.getId())==null) {
            userDAO.insert(user);
        }else userDAO.update(user);
    }

    public static void deleteUserById(UUID id) {
        if(id==null) {
            System.err.println("User's ID cannot be null");
        }else if(userDAO.getById(id)==null) {
            System.err.println("User not found");
        } else {
            userDAO.deleteById(id);
        }
    }

    public static User getUserById(UUID id){
        if(id==null) {
            System.err.println("User's ID cannot be null");
            return null;
        } else return userDAO.getById(id);
    }
}
