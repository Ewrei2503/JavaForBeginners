package by.yahor_kulesh.services;

import by.yahor_kulesh.model.tickets.Ticket;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TicketService {

    public static Ticket createLimitedTicket() {
        System.out.println("Input Concert hall:");
        String concertHall = InputValidator.inputString(10);
        System.out.println("Input Event code:");
        int eventCode = InputValidator.inputInt();
        System.out.println("Input Event time:");
        LocalDateTime time = InputValidator.inputTime();
        return new Ticket(concertHall, eventCode, ZonedDateTime.of(time.toLocalDate(),time.toLocalTime(), ZoneId.systemDefault()));
    }

    public static Ticket createFullTicket() {
        Ticket lim_ticket = createLimitedTicket();
        System.out.println("Is it Promo?:\n1.True\n0.False");
        boolean isPromo = InputValidator.inputBoolean();
        System.out.println("Input sector:");
        String sector = InputValidator.inputString(1).toUpperCase();
        System.out.println("Input allowed backpack weight:");
        double weight = InputValidator.inputBigDecimal(3).doubleValue();
        System.out.println("Input price:");
        BigDecimal price = InputValidator.inputBigDecimal(2);
        return new Ticket(lim_ticket, isPromo, sector, weight, price);
    }

}
