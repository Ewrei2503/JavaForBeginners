package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.tickets.BusTicket;
import by.yahor_kulesh.model.tickets.ConcertTicket;
import by.yahor_kulesh.model.tickets.Ticket;


public class Admin extends User {

    public Admin() {
    }

    public Admin(String name){
        this.setName(name);
    }

    public void checkTicket(Ticket ticket) {
        if(ticket.equals(new Ticket(ticket.getId())) || ticket.equals(new ConcertTicket(ticket.getId())) || ticket.equals(new BusTicket(ticket.getId()))){
            System.out.println("Ticket is wrong");
        } else System.out.println("Ticket is correct");
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "ID: " + this.getId() + "\nRole: " + this.getRole() + "\n\n\n";
    }
}
