package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.tickets.Ticket;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Client extends User {
  private Set<Ticket> tickets = new HashSet<>();

  public Client() {}

  public Client(String name) {
    this.setName(name);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Client)) return false;
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return "ID: "
        + this.getId()
        + "\nRole: "
        + this.getRole()
        + "\nTickets: "
        + tickets.size()
        + "\n\n";
  }
}
