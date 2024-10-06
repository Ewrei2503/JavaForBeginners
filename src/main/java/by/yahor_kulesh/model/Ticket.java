package by.yahor_kulesh.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Ticket {
    private String ID;
    private String concertHall;
    private String eventCode;
    private LocalDateTime date;
    private boolean isPromo;
    private char sector;
    private double backpackWeight;
    private final LocalDateTime ticketCreationTime = LocalDateTime.now();
    private BigDecimal price;

    public Ticket() {}

    public Ticket(String concertHall, String eventCode, LocalDateTime date) {
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
    }

    public Ticket(String ID, String concertHall, String eventCode, LocalDateTime date, boolean isPromo, char sector, double backpackWeight, BigDecimal price) {
        this.ID = ID;
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
        this.isPromo = isPromo;
        this.sector = sector;
        this.backpackWeight = backpackWeight;
        this.price = price;
    }

    public String getID() {
        return ID;
    }

    public String getConcertHall() {
        return concertHall;
    }

    public String getEventCode() {
        return eventCode;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public char getSector() {
        return sector;
    }

    public double getBackpackWeight() {
        return backpackWeight;
    }

    public LocalDateTime getTicketCreationTime() {
        return ticketCreationTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket Info:\n" +
                "ID: " + this.getID() +
                ";\nConcert Hall: " + this.getConcertHall() +
                ";\nEvent Code: " + this.getEventCode() +
                ";\nDate: " + (this.getDate() == null? null: this.getDate()) +
                ";\nPromo ticket: " + this.isPromo() +
                ";\nSector: " + this.getSector() +
                ";\nBackpack weight allowed: " + this.getBackpackWeight() +
                ";\nWas bought: " + this.getTicketCreationTime() +
                ";\nPrice: " + (this.getPrice()==null?0.0:this.getPrice()) +
                "$.\n\n\n";
    }
}
