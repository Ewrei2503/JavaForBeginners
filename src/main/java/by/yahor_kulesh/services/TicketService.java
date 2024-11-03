package by.yahor_kulesh.services;

import by.yahor_kulesh.dao.TicketDAO;
import by.yahor_kulesh.mappers.TicketMapper;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class TicketService extends Data{

    private final TicketDAO ticketDAO;
    private final File ticketDataFile;

    public TicketService(TicketDAO ticketDAO, File ticketDataFile) {
        this.ticketDAO = ticketDAO;
        this.ticketDataFile = ticketDataFile;
    }


    public void insertOrUpdateTicket(Ticket ticket){
        if(ticket==null || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID cannot be null");
            return;
        }
        if(getTicketById(ticket.getId())==null) {
            ticketDAO.insert(TicketMapper.INSTANCE.toEntity(ticket));
        } else ticketDAO.update(TicketMapper.INSTANCE.toEntity(ticket));
    }
    public void deleteTicketById(UUID id) {
        if(getTicketById(id)==null) {
            System.err.println("Ticket not found");
        } else {
            ticketDAO.deleteById(id);
        }
    }
    public Ticket getTicketById(UUID id){
        if(id==null){
            System.err.println("Ticket's ID cannot be null");
            return null;
        } else return TicketMapper.INSTANCE.toModel(ticketDAO.getById(id));
    }
    public List<Ticket> getTicketByUserId(UUID userId) {
        if(userId==null){
            System.err.println("User's ID cannot be null");
            return Collections.emptyList();
        }return TicketMapper.INSTANCE.toModel(ticketDAO.getByUserId(userId));
    }

    public void clientGetTicket(Ticket ticket, Client client) {
        ticket.setUserId(client.getId());
        if(ticketDAO.getById(ticket.getId())==null) {
            ticketDAO.insertTicketToUser(TicketMapper.INSTANCE.toEntity(ticket));
        } else ticketDAO.updateTicketToUser(TicketMapper.INSTANCE.toEntity(ticket));
        System.out.println("Client got ticket:" + ticket.getId() + "\n");
        client.getTickets().add(ticket);
    }


    public List<Ticket> getTicketsFromFile(){
        List<Ticket> tickets = new ArrayList<>();
        try(
            BufferedReader br = new BufferedReader(new FileReader((ticketDataFile)))
        ){
            String input;
             while((input = br.readLine())!=null) {
                tickets.add(getTicketFromString(input));
             }
        } catch (IOException e) {
            System.err.println("File not found!");
        }
        return tickets;
    }

    private Ticket getTicketFromString(String input){
        Ticket ticket;
        try {
            ticket = new ObjectMapper().readValue(input, Ticket.class);
        }
        catch (JsonProcessingException e) {
            input = input.replace('“','\"');
            return getTicketFromString(input);
        }
        return ticket;
    }
}