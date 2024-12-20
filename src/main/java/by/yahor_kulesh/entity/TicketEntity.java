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
import java.sql.Timestamp;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "ticket")
@NoArgsConstructor
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

  public TicketEntity(UUID ticketId) {
    this.id = ticketId;
    this.setCreationTime(new Timestamp(System.currentTimeMillis()));
  }
}
