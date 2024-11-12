package by.yahor_kulesh.utils;

import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Sector;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
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
    ConcertTicket concert = testTicket();
    testUser(concert);
    testUserDAO();
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

  private void testUser(ConcertTicket concert) {
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
  }

  private void testUserDAO() {
    Admin a = new Admin();
    userService.insertOrUpdateUser(a);
    Admin adm = (Admin) userService.getUserById(a.getId());
    System.out.println(a.equals(adm));
    userService.deleteUserById(a.getId());
  }
}
