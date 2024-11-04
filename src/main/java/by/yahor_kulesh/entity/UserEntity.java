package by.yahor_kulesh.entity;

import by.yahor_kulesh.entity.enums.Role;
import by.yahor_kulesh.entity.enums.UserStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;


@Data
@NoArgsConstructor
public class UserEntity {

    public UserEntity(UUID id) {
        this.id = id;
    }

    private UUID id;

    private String name;

    private Timestamp creationTime;

    private Role role;

    private UserStatus status;

    private Set<TicketEntity> tickets;
}