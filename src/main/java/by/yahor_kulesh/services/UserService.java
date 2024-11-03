package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.UserDAO;
import by.yahor_kulesh.mappers.UserMapper;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.users.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService extends Data {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void insertOrUpdateUser(User user) {
        if(user==null || user.getId()==null) {
            System.err.println("User or user's ID cannot be null");
            return;
        }if(userDAO.getById(user.getId())==null) {
            userDAO.insert(UserMapper.INSTANCE.toEntity(user));
        }else userDAO.update(UserMapper.INSTANCE.toEntity(user));
    }

    public void deleteUserById(UUID id) {
        if(id==null) {
            System.err.println("User's ID cannot be null");
        }else if(userDAO.getById(id)==null) {
            System.err.println("User not found");
        } else {
            userDAO.deleteById(id);
        }
    }

    public User getUserById(UUID id){
        if(id==null) {
            System.err.println("User's ID cannot be null");
            return null;
        } else return UserMapper.INSTANCE.toModel(userDAO.getById(id));
    }
}
