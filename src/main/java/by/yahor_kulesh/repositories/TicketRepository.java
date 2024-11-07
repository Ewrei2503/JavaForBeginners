package by.yahor_kulesh.repositories;

import by.yahor_kulesh.entity.TicketEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends CrudRepository<TicketEntity, UUID> {

    default void insertOrUpdateTicket(TicketEntity ticket){
        this.save(ticket);
    }

    TicketEntity getTicketById(UUID id);

    List<TicketEntity> getTicketByUserId(UUID userId);

    void removeTicketById(UUID id);
}
