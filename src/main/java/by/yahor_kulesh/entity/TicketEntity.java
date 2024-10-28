package by.yahor_kulesh.entity;

import by.yahor_kulesh.entity.enums.TicketType;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;


@Data
public class TicketEntity {
    private UUID id;

    private UserEntity user;

    private TicketType type;

    private Timestamp creationTime;


}