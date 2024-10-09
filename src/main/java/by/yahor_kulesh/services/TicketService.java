package by.yahor_kulesh.services;

import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Sector;
import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.model.users.Admin;
import by.yahor_kulesh.model.users.Client;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.ZoneId;

public class TicketService extends Data{
	public static void applicationMethod() {
		Client client = new Client();
		client.getTicket(new BusTicket(567.89));
		client.getTicket(new ConcertTicket("Concert",567,true, Sector.B.toString()));
		client.getTicket(new Ticket(InputValidator.inputTime("202502030405").atZone(ZoneId.systemDefault()), BigDecimal.valueOf(1234.567)));
		client.print();
		Admin admin = new Admin();
		admin.print();
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
		admin.checkTicket(new BusTicket());
		admin.checkTicket(concert);
	}
}
