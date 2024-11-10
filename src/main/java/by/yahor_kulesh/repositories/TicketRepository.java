package by.yahor_kulesh.repositories;

import by.yahor_kulesh.entity.TicketEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {

  TicketEntity getTicketById(UUID id);

  List<TicketEntity> getTicketByUserId(UUID userId);

  void removeTicketById(UUID id);
}
