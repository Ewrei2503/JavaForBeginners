package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Ticket;


public class Admin extends User {

    public void checkTicket(Ticket ticket) {
        if(ticket.equals(new Ticket(ticket.getId())) || ticket.equals(new ConcertTicket(ticket.getId())) || ticket.equals(new BusTicket(ticket.getId()))){
            System.out.println("Ticket is wrong");
        } else System.out.println("Ticket is correct");
    }

    @Override
    public String print() {
        return "ID: " + this.getId() + "\nRole: " + this.printRole() + "\n\n\n";
    }
}
