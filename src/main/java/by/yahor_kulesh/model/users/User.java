package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class User extends Data {
  private String name = "default_user";

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  public void printRole() {
    System.out.println(getRole());
  }

  public String getRole() {
    return this.getClass().getSimpleName();
  }
}
