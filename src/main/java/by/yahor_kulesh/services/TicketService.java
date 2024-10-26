package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.TicketDAO;
import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.mappers.TicketMapper;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.tickets.Ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TicketService extends Data{

    private static final TicketDAO ticketDAO = new TicketDAO();

    public static void insertOrUpdateTicket(Ticket ticket) {
        if(ticket==null || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID cannot be null");
            return;
        }if(getTicketById(ticket.getId())==null) {
            ticketDAO.saveTicket(TicketMapper.INSTANCE.toEntity(ticket));
        } else ticketDAO.updateTicket(TicketMapper.INSTANCE.toEntity(ticket));
    }

    public static void deleteTicketById(Ticket ticket) {
        if(ticket==null || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID is null");
        } else {
            ticketDAO.deleteTicketById(TicketMapper.INSTANCE.toEntity(ticket));
        }
    }

    public static Ticket getTicketById(UUID id){
        if(id==null){
            System.err.println("Ticket's ID cannot be null");
            return null;
        } else return TicketMapper.INSTANCE.toModel(ticketDAO.getTicketById(id));
    }

    public static List<Ticket> getTicketByUserId(UUID userId) {
        if(userId==null){
            System.err.println("User's ID cannot be null");
            return null;
        } else {
            List<Ticket> list = new ArrayList<>();
            List<TicketEntity> entities = ticketDAO.getTicketByUserId(userId);
            if(entities!=null) {
                for(TicketEntity ent: entities){
                    list.add(TicketMapper.INSTANCE.toModel(ent));
                }
            } else return null;
            return list;
        }
    }
}
