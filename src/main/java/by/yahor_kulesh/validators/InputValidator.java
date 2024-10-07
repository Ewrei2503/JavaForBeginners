package by.yahor_kulesh.validators;

import by.yahor_kulesh.exceptions.StringSizeException;
import by.yahor_kulesh.model.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.UUID;


public class InputValidator extends Data {

    private static final Scanner sc = new Scanner(System.in);

    public static int inputInt(){
        int input;
        try{
            input = sc.nextInt();
            sc.nextLine();
        }
        catch(InputMismatchException e){
            System.err.println("Not valid! Must contain integer number!");
            sc.next();
            input = inputInt();
        }
        return input;
    }

    public static BigDecimal inputBigDecimal(int scale) {
        BigDecimal d;
        try{
            d = sc.nextBigDecimal();
            sc.nextLine();
        } catch(InputMismatchException e){
            System.err.println("Not valid! Must contain floating 'COMMA' number!");
            sc.next();
            d = inputBigDecimal(scale);
        }
        return d.setScale(scale, RoundingMode.HALF_UP);
    }

    public static String inputString(){
        return sc.nextLine();
    }

    public static String inputString(int size){
        String input = sc.nextLine();
        try{
            if(input.length() > size){
                throw new StringSizeException();
            }
        }catch(StringSizeException e){
            System.err.println(e.getMessage());
            input = inputString(size);
        }
        return input;
    }

    public static boolean inputBoolean(){
        int input = inputInt();
        try{
            if(input > 1 | input < 0){
                throw new InputMismatchException("Must be 1(True) or 0(False)!");
            }
        } catch(InputMismatchException e){
            System.err.println(e.getMessage());
            sc.next();
            return inputBoolean();
        }
        return input == 1;
    }

    public static LocalDateTime inputTime(){
        String input = inputString();
        LocalDateTime localDateTime;
        try{
            localDateTime = LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            if(localDateTime.isBefore(LocalDateTime.now())){
                throw new InputMismatchException("You trying to buy ticket in the past!");
            }
        } catch(DateTimeParseException e){
            System.err.println("Not valid! Write time of event in YYYYMMDDHHmm format!");
            localDateTime = inputTime();
        } catch(InputMismatchException e){
            System.err.println(e.getMessage());
            localDateTime = inputTime();
        }
        return localDateTime;
    }


    @Override
    public UUID getId() {
        return super.id;
    }
}
