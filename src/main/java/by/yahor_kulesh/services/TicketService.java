package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.TicketDAO;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.utils.ObjectArray;

import java.util.UUID;

public class TicketService extends Data{

    private static final TicketDAO ticketDAO = new TicketDAO();

    public static void insertOrUpdateTicket(Ticket ticket) {
        if(ticket==null || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID cannot be null");
            return;
        }if(getTicketById(ticket.getId())==null) {
            ticketDAO.insert(ticket);
        } else ticketDAO.update(ticket);
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
        } else return ticketDAO.getById(id);
    }

    public static ObjectArray getTicketByUserId(UUID userId) {
        if(userId==null){
            System.err.println("User's ID cannot be null");
            return null;
        }return ticketDAO.getByUserId(userId);
    }


}
