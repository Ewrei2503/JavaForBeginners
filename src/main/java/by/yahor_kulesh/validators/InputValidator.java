package by.yahor_kulesh.validators;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;


public class InputValidator {

    private static final Scanner sc = new Scanner(System.in);

    public int inputInt(){
        int input;
        try{
            input = sc.nextInt();
        }
        catch(InputMismatchException e){
            System.out.println("Not valid! Must contain integer number!");
            sc.next();
            input = inputInt();
        }
        return input;
    }

    public BigDecimal inputBigDecimal(int scale) {
        BigDecimal d;
        try{
            d = sc.nextBigDecimal();
        } catch(InputMismatchException e){
            System.out.println("Not valid! Must contain floating 'COMMA' number!");
            sc.next();
            d = inputBigDecimal(scale);
        }
        return d.setScale(scale, RoundingMode.HALF_UP);
    }



}
