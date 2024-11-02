package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.TicketDAO;
import by.yahor_kulesh.mappers.TicketMapper;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.tickets.Ticket;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class TicketService extends Data{

    private static TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        TicketService.ticketDAO = ticketDAO;
    }


    public static void insertOrUpdateTicket(Ticket ticket){
        if(ticket==null || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID cannot be null");
            return;
        }
        if(getTicketById(ticket.getId())==null) {
            ticketDAO.insert(TicketMapper.INSTANCE.toEntity(ticket));
        } else ticketDAO.update(TicketMapper.INSTANCE.toEntity(ticket));
    }
    public static void deleteTicketById(UUID id) {
        if(getTicketById(id)==null) {
            System.err.println("Ticket not found");
        } else {
            ticketDAO.deleteById(id);
        }
    }
    public static Ticket getTicketById(UUID id){
        if(id==null){
            System.err.println("Ticket's ID cannot be null");
            return null;
        } else return TicketMapper.INSTANCE.toModel(ticketDAO.getById(id));
    }
    public static List<Ticket> getTicketByUserId(UUID userId) {
        if(userId==null){
            System.err.println("User's ID cannot be null");
            return Collections.emptyList();
        }return TicketMapper.INSTANCE.toModel(ticketDAO.getByUserId(userId));
    }
}