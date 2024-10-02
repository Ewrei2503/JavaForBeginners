package by.yahor_kulesh.model;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Ticket {
    private final UUID id = UUID.randomUUID();
    private final ZonedDateTime ticketCreationTime = ZonedDateTime.now();

    private String concertHall;
    private String eventCode;
    private ZonedDateTime date;
    private boolean isPromo;
    private Sector sector;
    private double backpackWeight;
    private BigDecimal price;

    public Ticket() {}

    public Ticket(String concertHall, String eventCode, ZonedDateTime date) {
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
    }

    public Ticket(String concertHall, String eventCode, ZonedDateTime date, boolean isPromo, Sector sector, double backpackWeight, BigDecimal price) {
        this.concertHall = concertHall;
        this.eventCode = eventCode;
        this.date = date;
        this.isPromo = isPromo;
        this.sector = sector;
        this.backpackWeight = backpackWeight;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getConcertHall() {
        return concertHall;
    }

    public String getEventCode() {
        return eventCode;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public Sector getSector() {
        return sector;
    }

    public double getBackpackWeight() {
        return backpackWeight;
    }

    public ZonedDateTime getTicketCreationTime() {
        return ticketCreationTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ticket Info:\n" +
                "ID: " + this.getId() +
                ";\nConcert Hall: " + this.getConcertHall() +
                ";\nEvent Code: " + this.getEventCode() +
                ";\nDate: " + (this.getDate() == null? null: this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nPromo ticket: " + this.isPromo() +
                ";\nSector: " + this.getSector() +
                ";\nBackpack weight allowed: " + this.getBackpackWeight() +
                ";\nWas bought: " + (this.getTicketCreationTime() == null? null: this.getTicketCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nPrice: " + (this.getPrice()==null?0.0:this.getPrice()) +
                "$.\n\n\n";
    }
}
