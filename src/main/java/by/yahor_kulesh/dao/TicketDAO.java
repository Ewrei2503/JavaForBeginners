package by.yahor_kulesh.dao;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.mappers.TicketMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
public class TicketDAO{

    private final JdbcTemplate jdbcTemplate;

    public TicketDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void insert(TicketEntity ticket) {

        jdbcTemplate.update("INSERT INTO ticket(id,user_id,ticket_type,creation_date) VALUES (?,?,?,?)",
                ticket.getId(),
                ticket.getUser()==null?null:ticket.getUser().getId(),
                ticket.getType().name(),
                ticket.getCreationTime()
        );
    }
    @Transactional
    public TicketEntity getById(UUID id) {
        return jdbcTemplate.query("SELECT * FROM ticket where id=?", TicketMapper.INSTANCE, id).stream().findAny().orElse(null);
    }

    @Transactional
    public List<TicketEntity> getByUserId(UUID id) {
        return jdbcTemplate.query("SELECT * FROM ticket where user_id=?", TicketMapper.INSTANCE, id);
    }

    @Transactional
    public void update(TicketEntity ticket){
        jdbcTemplate.update("UPDATE ticket SET id=?,user_id=?,ticket_type=?,creation_date=? WHERE id=?",
                ticket.getId(),
                ticket.getUser()==null?null:ticket.getUser().getId(),
                ticket.getType().name(),
                ticket.getCreationTime(),
                ticket.getId()
        );
    }

    @Transactional
    public void deleteById(UUID id){
        jdbcTemplate.update("DELETE FROM ticket WHERE id=?", id);
    }
}