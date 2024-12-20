package by.yahor_kulesh.model.tickets;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class BusTicket extends Ticket {
  private final double backpackWeight;

  public BusTicket() {
    super();
    this.backpackWeight = 0;
  }

  public BusTicket(UUID id) {
    super(id);
    this.backpackWeight = 0;
  }

  public BusTicket(Ticket ticket) {
    super(ticket);
    this.backpackWeight = 25;
  }

  public BusTicket(double backpackWeight) {
    this.backpackWeight = backpackWeight;
  }

  public BusTicket(Ticket ticket, double backpackWeight) {
    super(ticket);
    this.backpackWeight = backpackWeight;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof BusTicket busTicket)) return false;
    if (!super.equals(o)) return false;
    return Double.compare(backpackWeight, busTicket.backpackWeight) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), backpackWeight);
  }

  @Override
  public String toString() {
    return "Bus ticket Info:\n"
        + "ID: "
        + this.getId()
        + ";\nUser's ID: "
        + this.getUserId()
        + ";\nDate of depart: "
        + (Objects.isNull(this.getDate())
            ? null
            : this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        + ";\nBackpack weight allowed: "
        + this.getBackpackWeight()
        + ";\nWas bought: "
        + (Objects.isNull(this.getCreationTime())
            ? null
            : this.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME))
        + ";\nPrice: "
        + (Objects.isNull(this.getPrice()) ? 0.0 : this.getPrice())
        + "$.\n\n\n";
  }
}
