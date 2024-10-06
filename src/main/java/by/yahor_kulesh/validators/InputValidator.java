package by.yahor_kulesh.validators;

import java.math.BigDecimal;
import java.math.RoundingMode;


import static by.yahor_kulesh.services.TicketService.sc;

public class InputValidator {


    public static int inputInt(){
        int input = 0;
        try{
            input = sc.nextInt();
        }
        catch(NumberFormatException e){
            System.out.println("Not valid! Must contain digits!");
            inputInt();
        }
        return input;
    }

    public static BigDecimal inputBigDecimal(int scale) {
        BigDecimal d;
        try{
            d = sc.nextBigDecimal();
        } catch(NumberFormatException e){
            System.out.println("Not valid! Must contain double value!");
            d = inputBigDecimal(scale);
        }
        return d.setScale(scale, RoundingMode.HALF_UP);
    }


}
