package by.yahor_kulesh.services;

import by.yahor_kulesh.model.Sector;
import by.yahor_kulesh.model.Ticket;
import by.yahor_kulesh.validators.InputValidator;

import java.math.BigDecimal;
import java.time.*;
import java.util.Arrays;
import java.util.Scanner;

public class TicketService {
    private static final InputValidator inputValidator = new InputValidator();
    public static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        menu();
    }

    public static void menu(){
        while(true) {
            System.out.println("""
                        Welcome to the Ticket Service!
                        1.Create empty Ticket
                        2.Create limited ticket([Concert hall], [event code] and [time] required)
                        3.Create full ticket
                        0.Exit
                        """
            );
            switch (inputValidator.inputInt()){
                case 1:{
                    Ticket t = new Ticket();
                    System.out.println(t);
                    break;
                }
                case 2: {
                    System.out.println("Input Concert hall:");
                    String concertHall = inputStringOfSize(validateStringLimits(inputString(sc.nextLine()),new char[][]{{'A','Z'},{'a','z'}}), 10);
                    System.out.println("Input Event code:");
                    String eventCode = validateEventCode(inputValidator.inputInt());
                    System.out.println("Input Event time:");
                    ZonedDateTime time = inputTime();
                    Ticket ticket = new Ticket(concertHall, eventCode, time);
                    System.out.println(ticket);
                    break;
                }
                case 3: {
                    System.out.println("Writing full ticket:");
                    System.out.println("Input Concert hall:");
                    String concertHall = inputStringOfSize(validateStringLimits(inputString(sc.nextLine()),new char[][]{{'A','Z'},{'a','z'}}), 10);
                    System.out.println("Input Event code:");
                    String eventCode = validateEventCode(inputValidator.inputInt());
                    System.out.println("Input Event time:");
                    ZonedDateTime time = inputTime();
                    System.out.println("Is it Promo?:\n1.True\n2.False");
                    boolean isPromo = isPromo(inputValidator.inputInt());
                    System.out.println("Input sector:");
                    Sector sector = Enum.valueOf(Sector.class, inputStringOfSize(validateStringLimits(inputString(sc.nextLine()),new char[][]{{'A','C'}, {'a','c'}}), 1).toUpperCase());
                    System.out.println("Input allowed backpack weight:");
                    double weight = inputValidator.inputBigDecimal(3).doubleValue();
                    System.out.println("Input price:");
                    BigDecimal price = inputValidator.inputBigDecimal(2);
                    Ticket ticket = new Ticket(concertHall, eventCode, time, isPromo, sector, weight, price);
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

    public static String inputStringOfSize(String result, int size) {
        if(result.length()>size){
            System.out.println("Not valid! Must be size of " + size + " characters!");
            return inputStringOfSize(inputString(sc.nextLine()), size);
        } else {
            return result;
        }
    }

    public static String validateEventCode(int eventCode) {
        if (eventCode>0 & eventCode<10){
            return "00" + eventCode;
        }else if (eventCode > 9 & eventCode < 100) {
            return "0" + eventCode;
        } else if (eventCode > 100 & eventCode < 999) {
            return String.valueOf(eventCode);
        } else {
            System.out.println("Not valid! Must be digits between 0 and 999!");
            return validateEventCode(inputValidator.inputInt());
        }
    }


    public static ZonedDateTime inputTime() {
        System.out.println("Input Year:");
        int year = inputValidator.inputInt();
        while(year<1970){
            System.out.println("Try again!");
            year = inputValidator.inputInt();
        }
        System.out.println("Input Month Number:");
        int month = inputValidator.inputInt();
        while(month>12 || month<1){
            System.out.println("Try again!");
            month = inputValidator.inputInt();
        }
        System.out.println("Input Day Number:");
        int day = inputValidator.inputInt();
        while(
                ((day<1 || day>31) && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12)) ||
                        ((day<1 || day>30) && (month == 4 || month == 6 || month == 9 || month == 11)) || ((day<1 || day>28) && month == 2)
        ){
            System.out.println("Try again!");
            day = inputValidator.inputInt();
        }
        System.out.println("Input hour:");
        int hour = inputValidator.inputInt();
        while(hour<0 || hour>23){
            System.out.println("Try again!");
            hour = inputValidator.inputInt();
        }
        System.out.println("Input minutes:");
        int minutes = inputValidator.inputInt();
        while(minutes<0 || minutes>59){
            System.out.println("Try again!");
            minutes = inputValidator.inputInt();
        }
        return ZonedDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minutes), ZoneId.systemDefault());
    }


    public static String validateStringLimits(String input,char[][] limits){
        int lim=0;
        if(input.isEmpty()){
            System.out.println("Not valid! Must contain characters!");
            return validateStringLimits(inputString(sc.nextLine()),limits);
        }
        for(int str = 0;str<input.length();str++){
            for (char[] limit : limits) {
                if (input.charAt(str) >= limit[0] & input.charAt(str) <= limit[1]) {
                    break;
                } else lim++;
            }
            if(lim> limits.length-1){
                System.out.println("Not valid! Must be between " + Arrays.deepToString(limits));
                return validateStringLimits(inputString(sc.nextLine()),limits);
            } else lim=0;
        }
        return input;
    }


    public static boolean isPromo(int input){
        if (input < 1 || input > 2) {
            System.out.println("Not valid! Input 1(True) or 2(False)");
            return isPromo(inputValidator.inputInt());
        } else return input == 1;
    }


    public static String inputString(String input){
        if(input.isEmpty()){
            System.out.println("Not valid! Must contain required data!");
            return inputString(sc.nextLine());
        } else return input;
    }
}
