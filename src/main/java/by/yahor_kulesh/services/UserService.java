package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.UserDAO;
import by.yahor_kulesh.mappers.UserMapper;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.users.User;

import java.util.UUID;

public class UserService extends Data {
    private static final UserDAO userDAO = new UserDAO();


    public static void insertOrUpdateUser(User user) {
        if(user==null || user.getId()==null) {
            System.err.println("User or user's ID cannot be null");
            return;
        }if(userDAO.getUserById(user.getId())==null) {
            userDAO.saveUser(UserMapper.INSTANCE.toEntity(user));
        }else userDAO.updateUser(UserMapper.INSTANCE.toEntity(user));
    }

    public static void deleteUserById(User user) {
        if(user.getId()==null) {
            System.err.println("User's ID cannot be null");
        }else if(userDAO.getUserById(user.getId())==null) {
            System.err.println("User not found");
        } else {
            userDAO.deleteUser(UserMapper.INSTANCE.toEntity(user));
        }
    }

    public static User getUserById(UUID id){
        if(id==null) {
            System.err.println("User's ID cannot be null");
            return null;
        } else return UserMapper.INSTANCE.toModel(userDAO.getUserById(id));
    }
}
