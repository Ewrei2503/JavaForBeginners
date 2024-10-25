package by.yahor_kulesh.entity;

import by.yahor_kulesh.entity.enums.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;


@Data
@Entity
@Table(name="usr")
public class UserEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "creation_date")
    private Timestamp creationTime;

    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<TicketEntity> tickets;
}
