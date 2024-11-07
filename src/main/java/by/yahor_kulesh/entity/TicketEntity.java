package by.yahor_kulesh.entity;

import by.yahor_kulesh.entity.enums.TicketType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;


@Data
@Entity
@Table(name = "ticket")
public class TicketEntity {
    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Column(name = "ticket_type")
    @Enumerated(EnumType.STRING)
    private TicketType type;

    @Column(name = "creation_date")
    private Timestamp creationTime;


}