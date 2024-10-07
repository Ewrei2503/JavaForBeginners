package by.yahor_kulesh.model.tickets;

import java.time.format.DateTimeFormatter;

public class BusTicket extends Ticket{
    private final double backpackWeight;

    public BusTicket() {
        super();
        this.backpackWeight = 0;
    }

    public BusTicket(double backpackWeight) {
        this.backpackWeight = backpackWeight;
    }

    public BusTicket(Ticket ticket, double backpackWeight) {
        super(ticket);
        this.backpackWeight = backpackWeight;
    }

    public double getBackpackWeight() {
        return backpackWeight;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "backpackWeight=" + backpackWeight +
                "} " + super.toString();
    }

    @Override
    public String print() {
        return "Bus ticket Info:\n" +
                "ID: " + this.getId() +
                ";\nDate of depart: " + (this.getDate() == null? null: this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nBackpack weight allowed: " + this.getBackpackWeight() +
                ";\nWas bought: " + (this.getTicketCreationTime() == null? null: this.getTicketCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nPrice: " + (this.getPrice()==null?0.0:this.getPrice()) +
                "$.\n\n\n";
    }
}
