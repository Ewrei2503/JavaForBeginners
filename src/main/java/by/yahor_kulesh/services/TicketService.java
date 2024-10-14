package by.yahor_kulesh.services;

import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.Printable;
import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Sector;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.model.users.User;
import by.yahor_kulesh.repositories.TicketRepo;
import by.yahor_kulesh.repositories.UserRepo;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.ZoneId;

public class TicketService extends Data{
	public static void testTicketService() {
        ConcertTicket concert = testTicket();
        Client client = testUser(concert);

        try{
            testTicketRepo();
        }catch(IndexOutOfBoundsException e){
            System.err.println(e.getMessage());
        }
        testUserRepo(client);
    }

    private static ConcertTicket testTicket() {
        Ticket ticket = new Ticket(InputValidator.inputTime("202501010101").atZone(ZoneId.systemDefault()), BigDecimal.valueOf(1234.123));
        ticket.print();
        ConcertTicket concert = new ConcertTicket(ticket,"Hall",123,false, Sector.A.toString());
        concert.print();
        BusTicket busTicket = new BusTicket(ticket,123.1237);
        busTicket.print();
        ticket.share("+375291234567");
        busTicket.share("+375291234567");
        concert.share("e.k.02@out.com");
        concert.share("e.k.02@out.com", "+375292283213");
        return concert;
    }

    private static Client testUser(ConcertTicket concert) {
        Client client = new Client();
        client.getTicket(new BusTicket(567.89));
        client.getTicket(new ConcertTicket("Concert",567,true, Sector.B.toString()));
        client.getTicket(new Ticket(InputValidator.inputTime("202502030405").atZone(ZoneId.systemDefault()), BigDecimal.valueOf(1234.567)));
        client.print();
        Admin admin = new Admin();
        admin.print();
        admin.checkTicket(new BusTicket());
        admin.checkTicket(concert);
        return client;
    }

    private static void testUserRepo(Client client) {
        User u2 = new Client();
        u2.setId(client.getId());
        System.out.println("\n\n\nu2 user is equal to client: " + u2.equals(client));

        UserRepo userRepo = new UserRepo();
        System.out.println("Result of inputting client value:" + userRepo.add(client));
        System.out.println("Result of input similar as client value:" + userRepo.add(u2));
        System.out.println("User repo contains u2 or client:" + userRepo.contains(u2));
        System.out.println("Result of input other value:" + userRepo.add(new Admin()) + "\n\n\n");
        System.out.println("----------------------------------");
        userRepo.iterate(Printable::print);
        System.out.println("User repo value was removed: " + !userRepo.remove(u2));
        System.out.println("----------------------------------");
        userRepo.iterate(Printable::print);
    }

    private static void testTicketRepo() {
        TicketRepo ticketRepo = new TicketRepo();
        for(int i = 0;i<13;i++){
            ticketRepo.add(new Ticket());
        }
        System.out.println(ticketRepo.getByIndex(11));
        ticketRepo.removeByIndex(5);
        System.out.println(ticketRepo.getByIndex(10));
    }
}
