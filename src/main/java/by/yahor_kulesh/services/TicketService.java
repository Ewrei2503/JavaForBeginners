package by.yahor_kulesh.services;

import by.yahor_kulesh.entity.TicketEntity;
import by.yahor_kulesh.mappers.TicketMapper;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.repositories.TicketRepository;
import by.yahor_kulesh.repositories.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TicketService {

  private final TicketRepository ticketRepository;
  private final UserRepository userRepository;

  @Transactional
  public void insertOrUpdateTicket(Ticket ticket) {
    if (Objects.isNull(ticket) || Objects.isNull(ticket.getId())) {
      throw new IllegalArgumentException("Ticket or ticket's ID cannot be null");
    }
    ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
  }

  @Transactional
  public void deleteTicketById(UUID id) {
    if (Objects.isNull(TicketMapper.INSTANCE.toModel(ticketRepository.getTicketById(id)))) {
      throw new IllegalArgumentException("Ticket not found");
    } else {
      ticketRepository.deleteById(id);
    }
  }

  @Transactional(readOnly = true)
  public Ticket getTicketById(UUID id) {
    if (Objects.isNull(id)) {
      throw new IllegalArgumentException("Ticket's ID cannot be null");
    } else return TicketMapper.INSTANCE.toModel(ticketRepository.getTicketById(id));
  }

  @Transactional(readOnly = true)
  public List<Ticket> getTicketByUserId(UUID userId) {
    if (Objects.isNull(userId)) {
      throw new IllegalArgumentException("User's ID cannot be null");
    }
    return TicketMapper.INSTANCE.toModel(ticketRepository.getTicketByUserId(userId));
  }

  @Transactional
  public void insertOrUpdateTicketAndUpdateClient(Ticket ticket, Client client) {
    if (Objects.isNull(ticket) || Objects.isNull(client)) {
      throw new IllegalArgumentException("Ticket or Client cannot be null");
    } else if (Objects.isNull(userRepository.getUserById(client.getId()))) {
      throw new IllegalArgumentException("User not found");
    }
    ticket.setUserId(client.getId());
    ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
    userRepository.updateUserStatusById(client.getId());
    System.out.println("Client got ticket:" + ticket.getId() + "\n");
    client.getTickets().add(ticket);
  }

  public List<Ticket> getTicketsFromFile(File file) {
    List<Ticket> tickets = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader((file)))) {
      String input;
      while ((input = br.readLine()) != null) {
        tickets.add(getTicketFromString(input));
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("File not found!");
    }
    return tickets;
  }

  private Ticket getTicketFromString(String input) {
    Ticket ticket;
    try {
      ticket = new ObjectMapper().readValue(input, Ticket.class);
    } catch (JsonProcessingException e) {
      input = input.replace('“', '\"');
      return getTicketFromString(input);
    }
    return ticket;
  }

  @Transactional
  public TicketEntity getOrCreateTicket(UUID id) {
    if (Objects.isNull(id)) {
      Ticket ticket = new Ticket();
      ticketRepository.save(TicketMapper.INSTANCE.toEntity(ticket));
      return ticketRepository.getTicketById(ticket.getId());
    } else if (Objects.isNull(ticketRepository.getTicketById(id))) {
      ticketRepository.save(TicketMapper.INSTANCE.toEntity(new Ticket(id)));
    }
    return ticketRepository.getTicketById(id);
  }
}
