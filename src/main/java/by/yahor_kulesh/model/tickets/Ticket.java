package by.yahor_kulesh.model.tickets;

import by.yahor_kulesh.exceptions.OutOfLimitsException;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Ticket extends Data{
    private final ZonedDateTime ticketCreationTime = ZonedDateTime.now();

    private final String concertHall;
    private final String eventCode;
    private ZonedDateTime date;
    private final boolean isPromo;
    private Sector sector;
    private final double backpackWeight;
    private final BigDecimal price;

    public Ticket() {
        this.concertHall = "";
        this.eventCode = "";
        this.date = null;
        this.isPromo = false;
        this.sector = null;
        this.backpackWeight = 0;
        this.price = null;
    }

    public Ticket(String concertHall, int eventCode, ZonedDateTime date) {
        this.concertHall = validateStringLimits(concertHall, "Concert Hall", new char[][]{{'A','Z'}, {'a','z'}});
        this.eventCode = validateEventCode(eventCode);
        this.date = date;
        this.isPromo = false;
        this.backpackWeight = 0;
        this.price = null;
    }

    public Ticket(Ticket lim_ticket, boolean isPromo, String sector, double backpackWeight, BigDecimal price) {
        this.concertHall = validateStringLimits(lim_ticket.getConcertHall(), "Concert Hall", new char[][]{{'A','Z'}, {'a','z'}});
        this.eventCode = validateEventCode(Integer.parseInt(lim_ticket.getEventCode()));
        this.date = lim_ticket.getDate();
        this.isPromo = isPromo;
        setSector(sector);
        this.backpackWeight = backpackWeight;
        this.price = price;
    }


    public void setSector(String sector) {
        this.sector = Sector.valueOf(validateStringLimits(sector,"Sector" ,new char[][]{{'A','C'}, {'a','c'}}));
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }



    @Override
    public UUID getId() {
        return super.id;
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
