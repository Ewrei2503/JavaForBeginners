package by.yahor_kulesh;

import by.yahor_kulesh.model.Ticket;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;


public class App {

    public static void main(String[] args) {
        while(true) {
            System.out.println("""
                        Welcome to the Ticket Service!
                        1.Create empty Ticket
                        2.Create limited ticket([Concert hall], [event code] and [time] required)
                        3.Create full ticket
                        0.Exit
                        """
            );
            switch (InputValidator.inputInt()){
                case 1:{
                    Ticket t = new Ticket();
                    System.out.println(t);
                    break;
                }
                case 2: {
                    System.out.println("Input Concert hall:");
                    String concertHall = InputValidator.inputString(10);
                    System.out.println("Input Event code:");
                    int eventCode = InputValidator.inputInt();
                    System.out.println("Input Event time:");
                    LocalDateTime time = InputValidator.inputTime();
                    Ticket ticket = new Ticket(concertHall, eventCode, ZonedDateTime.of(time.toLocalDate(),time.toLocalTime(), ZoneId.systemDefault()));
                    System.out.println(ticket);
                    break;
                }
                case 3: {
                    System.out.println("Writing full ticket:");
                    System.out.println("Input Concert hall:");
                    String concertHall = InputValidator.inputString(10);
                    //String concertHall = inputStringOfSize(validateStringLimits(inputString(sc.nextLine()),new char[][]{{'A','Z'},{'a','z'}}), 10);
                    System.out.println("Input Event code:");
                    int eventCode = InputValidator.inputInt();
                    System.out.println("Input Event time (yyyyMMddmmss):");
                    LocalDateTime time = InputValidator.inputTime();
                    System.out.println("Is it Promo?:\n1.True\n0.False");
                    boolean isPromo = InputValidator.inputBoolean();
                    System.out.println("Input sector:");
                    String sector = InputValidator.inputString(1);
                    System.out.println("Input allowed backpack weight:");
                    double weight = InputValidator.inputBigDecimal(3).doubleValue();
                    System.out.println("Input price:");
                    BigDecimal price = InputValidator.inputBigDecimal(2);
                    Ticket ticket = new Ticket(concertHall, eventCode, ZonedDateTime.of(time.toLocalDate(),time.toLocalTime(), ZoneId.systemDefault()), isPromo, sector, weight, price);
                    System.out.println(ticket);
                    break;
                }
                case 0: {
                    return;
                }
                default: {
                    System.out.println("Try again!");
                }
            }
        }
    }
}
