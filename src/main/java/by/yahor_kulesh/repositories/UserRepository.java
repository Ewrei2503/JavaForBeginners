package by.yahor_kulesh.repositories;

import by.yahor_kulesh.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

  UserEntity getUserById(UUID id);

  void removeUserById(UUID id);

  /**
   * Required to declare here {@code @Transactional} manually in case of using {@code @Query} - else
   * InvalidDataAccessApiUsageException handles
   */
  @Transactional
  @Modifying
  @Query(value = "update UserEntity u set u.status='ACTIVATED' where u.id =:id")
  void updateUserStatusById(@Param("id") UUID id);
}
