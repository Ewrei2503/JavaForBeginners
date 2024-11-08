package by.yahor_kulesh.services;

import by.yahor_kulesh.mappers.UserMapper;
import by.yahor_kulesh.model.users.User;
import by.yahor_kulesh.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void insertOrUpdateUser(User user) {
        if(user==null || user.getId()==null) {
            System.err.println("User or user's ID cannot be null");
            return;
        }
        userRepository.save(UserMapper.INSTANCE.toEntity(user));
    }

    @Transactional
    public void deleteUserById(UUID id) {
        if(id==null) {
            System.err.println("User's ID cannot be null");
        }else if(userRepository.getUserById(id)==null) {
            System.err.println("User not found");
        } else {
            userRepository.removeUserById(id);
        }
    }

    @Transactional(readOnly = true)
    public User getUserById(UUID id){
        if(id==null) {
            System.err.println("User's ID cannot be null");
            return null;
        } else return UserMapper.INSTANCE.toModel(userRepository.getUserById(id));
    }
}
