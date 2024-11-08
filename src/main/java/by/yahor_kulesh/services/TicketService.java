package by.yahor_kulesh.services;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.mappers.TicketMapper;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.repositories.TicketRepository;
import by.yahor_kulesh.repositories.UserRepository;
import by.yahor_kulesh.utils.DataTestClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;



@Service
public class TicketService{

    private final File ticketDataFile;
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public TicketService(File ticketDataFile, TicketRepository ticketRepository, UserRepository userRepository, UserService userService) {
        this.ticketDataFile = ticketDataFile;
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Transactional
    public void insertOrUpdateTicket(Ticket ticket){
        if(Objects.isNull(ticket) || ticket.getId()==null) {
            System.err.println("Ticket or ticket's ID cannot be null");
            return;
        }
        ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
    }

    @Transactional
    public void deleteTicketById(UUID id) {
        if(Objects.isNull(TicketMapper.INSTANCE.toModel(ticketRepository.getTicketById(id)))) {
            System.err.println("Ticket not found");
        } else {
            ticketRepository.deleteById(id);
        }
    }

    @Transactional(readOnly = true)
    public Ticket getTicketById(UUID id){
        if(Objects.isNull(id)){
            System.err.println("Ticket's ID cannot be null");
            return null;
        } else return TicketMapper.INSTANCE.toModel(ticketRepository.getTicketById(id));
    }

    @Transactional(readOnly = true)
    public List<Ticket> getTicketByUserId(UUID userId) {
        if(userId==null){
            System.err.println("User's ID cannot be null");
            return Collections.emptyList();
        }return TicketMapper.INSTANCE.toModel(ticketRepository.getTicketByUserId(userId));
    }

    @Transactional
    public void insertOrUpdateTicketAndUpdateClient(Ticket ticket, Client client) {
            ticket.setUserId(client.getId());
            ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
            userRepository.updateUserStatusById(client.getId());
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

    @Transactional
    public TicketEntity getOrCreateTicket(UUID id) {
        if(Objects.isNull(id)){
            Ticket ticket = new Ticket();
            ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
            return ticketRepository.getTicketById(ticket.getId());
        }else if(ticketRepository.getTicketById(id)==null) {
            ticketRepository.save(TicketMapper.INSTANCE.toEntity(new Ticket(id)));
        }
        return ticketRepository.getTicketById(id);
    }

    public void testApp() {
        DataTestClass testClass = new DataTestClass(this,userService);
        testClass.testTicketService();
    }
}