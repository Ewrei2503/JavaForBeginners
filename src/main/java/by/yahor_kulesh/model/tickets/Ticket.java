package by.yahor_kulesh.model.tickets;

import by.yahor_kulesh.exceptions.OutOfLimitsException;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.validators.InputValidator;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Getter
public class Ticket extends Data {
    private final BigDecimal price;

    @Setter
    private ZonedDateTime date;

    @Setter
    private UUID userId;




    public Ticket() {
        this.date = null;
        this.price = null;
        this.userId = null;
    }

    public Ticket(UUID id) {
        super.setId(id);
        this.date = null;
        this.price = null;
        this.userId = null;
    }

    public Ticket(ZonedDateTime date, BigDecimal price) {
        this.date = date;
        this.price = price;
        this.userId = null;
    }

    public Ticket(Ticket ticket) {
        super.setId(ticket.getId());
        this.date = ticket.date;
        this.price = ticket.price;
        this.userId = ticket.userId;
    }


    /**I've deleted from here ticket creation time,
     * because it will be always false
     * */
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Ticket ticket)) return false;
        if(!super.equals(o)) return false;
        return Objects.equals(this.getId(), ticket.getId()) && Objects.equals(price, ticket.price) && Objects.equals(date, ticket.date) && Objects.equals(userId, ticket.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getId(), super.getCreationTime(), price, date);
    }

    @Override
    public String toString() {
        return "Ticket Info:\n" +
                       "ID: " + this.getId() +
                       ";\nUser's ID: " + this.getUserId() +
                       ";\nWas bought: " + (this.getCreationTime() == null? null: this.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                       ";\nDate of event: " + (this.getDate() == null? null: this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +

                       ";\nPrice: " + (this.getPrice()==null?0.0:this.getPrice()) +
                       "$.\n\n\n";
    }



    public void share(String phone){
        System.out.println(this.getClass().getSimpleName() + " was send to " + phone + "\n");
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
