package by.yahor_kulesh.model.tickets;

import by.yahor_kulesh.exceptions.OutOfLimitsException;
import by.yahor_kulesh.model.Data;
import by.yahor_kulesh.validators.InputValidator;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ticket extends Data {
  private final BigDecimal price;

  private ZonedDateTime date;
  private UUID userId;
  private String ticketType;
  private String ticketClass;
  private String startDate;

  public Ticket() {
    this.date = null;
    this.price = null;
  }

  public Ticket(UUID id) {
    super.setId(id);
    this.date = null;
    this.price = null;
  }

  public Ticket(ZonedDateTime date, BigDecimal price) {
    this.date = date;
    this.price = price;
  }

  public Ticket(Ticket ticket) {
    super.setId(ticket.getId());
    this.date = ticket.date;
    this.price = ticket.price;
    this.userId = ticket.userId;
  }

  public String getDateOfEvent() {
    if (!(Objects.isNull(this.getStartDate()))) {
      return this.getStartDate();
    } else if (Objects.isNull((this.getDate()))) {
      return null;
    } else {
      return this.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Ticket ticket)) return false;
    if (!super.equals(o)) return false;
    return Objects.equals(this.getId(), ticket.getId())
        && Objects.equals(price, ticket.price)
        && Objects.equals(date, ticket.date)
        && Objects.equals(userId, ticket.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getId(), super.getCreationTime(), price, date);
  }

  @Override
  public String toString() {
    return "Ticket Info:\n"
        + "ID: "
        + this.getId()
        + ";\nUser's ID: "
        + this.getUserId()
        + ";\nTicket Class: "
        + this.getTicketClass()
        + ";\nTicket Type: "
        + this.getTicketType()
        + ";\nWas bought: "
        + ((Objects.isNull(this.getCreationTime()))
            ? null
            : this.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        + ";\nDate of event: "
        + this.getDateOfEvent()
        + ";\nPrice: "
        + ((Objects.isNull(this.getPrice())) ? 0.0 : this.getPrice())
        + "$.\n\n\n";
  }

  public void share(String phone) {
    System.out.println(this.getClass().getSimpleName() + " was send to " + phone + "\n");
  }

  public static String validateStringLimits(String input, String variable, char[][] limits) {
    int lim = 0;
    try {
      for (int str = 0; str < input.length(); str++) {
        for (char[] limit : limits) {
          if (input.charAt(str) >= limit[0] && input.charAt(str) <= limit[1]) {
            break;
          } else lim++;
        }
        if (lim > limits.length - 1) {
          throw new OutOfLimitsException(limits, variable);
        } else lim = 0;
      }
    } catch (OutOfLimitsException e) {
      System.err.println(e.getMessage());
      input = validateStringLimits(InputValidator.inputString(input.length()), variable, limits);
    }
    return input;
  }

  public static String validateEventCode(int eventCode) {
    if (eventCode > 0 && eventCode < 10) {
      return "00" + eventCode;
    } else if (eventCode > 9 && eventCode < 100) {
      return "0" + eventCode;
    } else if (eventCode > 100 && eventCode < 999) {
      return String.valueOf(eventCode);
    } else {
      System.err.println("Event code is not valid! Must be digits between 0 and 999! Write again:");
      return validateEventCode(InputValidator.inputInt());
    }
  }
}
