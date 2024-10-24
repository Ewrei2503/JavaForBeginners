package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.services.TicketService;

import java.util.HashSet;
import java.util.Set;

public class Client extends User {
    private final Set<Ticket> tickets = new HashSet<>();

    public Client() {
    }

    public Client(String name){
        this.setName(name);
    }

    public void getTicket(Ticket ticket) {
        ticket.setUserId(this.getId());
        TicketService.insertOrUpdateTicket(ticket);
        System.out.println("Client got ticket:" + ticket.getId() + "\n");
        tickets.add(ticket);

    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Client client)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + "\nRole: " + this.getRole() + "\nTickets: " +  tickets.size() + "\n\n";
    }
}
