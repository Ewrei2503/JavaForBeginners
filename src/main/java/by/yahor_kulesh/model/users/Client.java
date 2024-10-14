package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.tickets.Ticket;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Client extends User {
    private final Set<Ticket> tickets = new HashSet<>();

    public void getTicket(Ticket ticket) {
        System.out.println("Client got ticket:" + ticket.getId() + "\n");
        tickets.add(ticket);

    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Client client)) return false;
        return Objects.equals(tickets, client.tickets) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void print() {
	    System.out.println("ID: " + this.getId() + "\nRole: " + this.printRole() + "\nTickets: " +  tickets.size() + "\n\n");
    }
}
