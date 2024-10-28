package by.yahor_kulesh.entity;

import by.yahor_kulesh.entity.enums.Role;
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

    private Set<TicketEntity> tickets;
}