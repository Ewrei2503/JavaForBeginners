package by.yahor_kulesh.services;

import by.yahor_kulesh.mappers.UserMapper;
import by.yahor_kulesh.model.users.User;
import by.yahor_kulesh.repositories.UserRepository;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserRepository userRepository;

  @Transactional
  public void insertOrUpdateUser(User user) {
    if (Objects.isNull(user) || Objects.isNull(user.getId())) {
      System.err.println("User or user's ID cannot be null");
      return;
    }
    userRepository.save(UserMapper.INSTANCE.toEntity(user));
  }

  @Transactional
  public void deleteUserById(UUID id) {
    if (Objects.isNull(id)) {
      System.err.println("User's ID cannot be null");
    } else if (Objects.isNull(userRepository.getUserById(id))) {
      System.err.println("User not found");
    } else {
      userRepository.removeUserById(id);
    }
  }

  @Transactional(readOnly = true)
  public User getUserById(UUID id) {
    if (Objects.isNull(id)) {
      System.err.println("User's ID cannot be null");
      return null;
    } else return UserMapper.INSTANCE.toModel(userRepository.getUserById(id));
  }
}
