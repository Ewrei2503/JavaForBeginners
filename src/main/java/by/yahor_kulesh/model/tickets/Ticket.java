package by.yahor_kulesh.model.tickets;

import by.yahor_kulesh.exceptions.OutOfLimitsException;
import by.yahor_kulesh.validators.InputValidator;

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

    public Ticket(String concertHall, int eventCode, ZonedDateTime date) {
        setConcertHall(concertHall);
        setEventCode(eventCode);
        this.date = date;
    }

    public Ticket(Ticket lim_ticket, boolean isPromo, String sector, double backpackWeight, BigDecimal price) {
        setConcertHall(lim_ticket.getConcertHall());
        setEventCode(Integer.parseInt(lim_ticket.getEventCode()));
        this.date = lim_ticket.getDate();
        this.isPromo = isPromo;
        setSector(sector);
        this.backpackWeight = backpackWeight;
        this.price = price;
    }

    public void setConcertHall(String concertHall) {
        this.concertHall = validateStringLimits(concertHall, "Concert Hall", new char[][]{{'A','Z'}, {'a','z'}});
    }

    public void setEventCode(int eventCode) {
        this.eventCode = validateEventCode(eventCode);
    }

    public void setSector(String sector) {
        this.sector = Sector.valueOf(validateStringLimits(sector,"Sector" ,new char[][]{{'A','C'}, {'a','c'}}));
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

    public static String validateStringLimits(String input, String variable, char[][] limits){
        int lim=0;
        try {
            for (int str = 0; str < input.length(); str++) {
                for (char[] limit : limits) {
                    if (input.charAt(str) >= limit[0] & input.charAt(str) <= limit[1]) {
                        break;
                    } else lim++;
                }
                if (lim > limits.length - 1) {
                    throw new OutOfLimitsException(limits, variable);
                } else lim = 0;
            }
        } catch (OutOfLimitsException e){
            System.err.println(e.getMessage());
            input = validateStringLimits(InputValidator.inputString(input.length()),variable,limits);
        }
        return input;
    }

    public static String validateEventCode(int eventCode) {
        if (eventCode>0 & eventCode<10){
            return "00" + eventCode;
        }else if (eventCode > 9 & eventCode < 100) {
            return "0" + eventCode;
        } else if (eventCode > 100 & eventCode < 999) {
            return String.valueOf(eventCode);
        } else {
            System.err.println("Event code is not valid! Must be digits between 0 and 999! Write again:");
            return validateEventCode(InputValidator.inputInt());
        }
    }
}
