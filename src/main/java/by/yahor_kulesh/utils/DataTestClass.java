package by.yahor_kulesh.utils;

import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Sector;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.model.users.User;
import by.yahor_kulesh.services.TicketService;
import by.yahor_kulesh.services.UserService;
import by.yahor_kulesh.validators.InputValidator;
import java.math.BigDecimal;
import java.time.ZoneId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DataTestClass {

  public final TicketService ticketService;
  public final UserService userService;

  public void testTicketService() {
    readTicketsFromFile();
    ConcertTicket concert = testTicket();
    Client client = testUser(concert);
    ObjectArray ticketRepo = null;
    try {
      ticketRepo = testObjectArray();
    } catch (IndexOutOfBoundsException e) {
      System.err.println(e.getMessage());
    }
    testObjectSet(client);
    testTicketDAO(ticketRepo, client);
    testUserDAO();
  }

  private void readTicketsFromFile() {
    System.out.println(ticketService.getTicketsFromFile());
  }

  private ConcertTicket testTicket() {
    Ticket ticket =
        new Ticket(
            InputValidator.inputTime("202501010101").atZone(ZoneId.systemDefault()),
            BigDecimal.valueOf(1234.123));
    ticket.print();
    ConcertTicket concert = new ConcertTicket(ticket, "Hall", 123, false, Sector.A.toString());
    concert.print();
    BusTicket busTicket = new BusTicket(ticket, 123.1237);
    busTicket.print();
    ticket.share("+375291234567");
    busTicket.share("+375291234567");
    concert.share("e.k.02@out.com");
    concert.share("e.k.02@out.com", "+375292283213");
    return concert;
  }

  private Client testUser(ConcertTicket concert) {
    Client client = new Client();
    userService.insertOrUpdateUser(client);
    ticketService.insertOrUpdateTicketAndUpdateClient(new BusTicket(567.89), client);
    ticketService.insertOrUpdateTicketAndUpdateClient(
        new ConcertTicket("Concert", 567, true, Sector.B.toString()), client);
    ticketService.insertOrUpdateTicketAndUpdateClient(
        new Ticket(
            InputValidator.inputTime("202502030405").atZone(ZoneId.systemDefault()),
            BigDecimal.valueOf(1234.567)),
        client);
    client.print();
    Admin admin = new Admin();
    admin.print();
    admin.checkTicket(new BusTicket());
    admin.checkTicket(concert);
    return client;
  }

  private void testObjectSet(Client client) {
    User u2 = new Client();
    u2.setId(client.getId());
    System.out.println("\n\n\nu2 user is equal to client: " + u2.equals(client));

    ObjectSetAsArray userRepo = new ObjectSetAsArray();
    System.out.println("Result of inputting client value:" + userRepo.add(client));
    System.out.println("Result of input similar as client value:" + userRepo.add(u2));
    System.out.println("User repo contains u2 or client:" + userRepo.contains(u2));
    System.out.println("Result of input other value:" + userRepo.add(new Admin()) + "\n\n\n");
    System.out.println("----------------------------------");
    userRepo.iterate(System.out::println);
    System.out.println("User repo value was removed: " + !userRepo.remove(u2));
    System.out.println("----------------------------------");
    userRepo.iterate(System.out::println);
  }

  private ObjectArray testObjectArray() {
    ObjectArray ticketRepo = new ObjectArray();
    for (int i = 0; i < 13; i++) {
      ticketRepo.add(new Ticket());
    }
    System.out.println(ticketRepo.getByIndex(11));
    ticketRepo.removeByIndex(5);
    System.out.println(ticketRepo.getByIndex(10));
    return ticketRepo;
  }

  private void testUserDAO() {
    Admin a = new Admin();
    userService.insertOrUpdateUser(a);
    Admin adm = (Admin) userService.getUserById(a.getId());
    System.out.println(a.equals(adm));
    userService.deleteUserById(a.getId());
  }

  private void testTicketDAO(ObjectArray ticketRepo, Client client) {
    Ticket ticket = null;
    for (int i = 0; i < ticketRepo.size(); i++) {
      ticketService.insertOrUpdateTicket(
          ticketRepo.getByIndex(i) instanceof ConcertTicket
              ? (ConcertTicket) ticketRepo.getByIndex(i)
              : (ticketRepo.getByIndex(i) instanceof BusTicket
                  ? (BusTicket) ticketRepo.getByIndex(i)
                  : (Ticket) ticketRepo.getByIndex(i)));
      if (i == 2) {
        ticket =
            ticketRepo.getByIndex(i) instanceof ConcertTicket
                ? (ConcertTicket) ticketRepo.getByIndex(i)
                : (ticketRepo.getByIndex(i) instanceof BusTicket
                    ? (BusTicket) ticketRepo.getByIndex(i)
                    : (Ticket) ticketRepo.getByIndex(i));
      }
    }
    System.out.println(
        ticketService.getTicketById(ticket.getId())
            + "\n-----------------------------------------------");
    System.out.println(
        ticketService.getTicketByUserId(client.getId())
            + "\n-----------------------------------------------");
    ticketService.insertOrUpdateTicket(new BusTicket(ticket));
    System.out.println(
        ticketService.getTicketById(ticket.getId())
            + "\n----------------------------------------------");
    ticketService.deleteTicketById(ticket.getId());
  }
}
