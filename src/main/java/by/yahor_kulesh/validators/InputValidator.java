package by.yahor_kulesh.validators;

import by.yahor_kulesh.exceptions.StringSizeException;

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
            System.err.println("Not valid! Must contain integer number!");
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
            System.err.println("Not valid! Must contain floating 'COMMA' number!");
            sc.next();
            d = inputBigDecimal(scale);
        }
        return d.setScale(scale, RoundingMode.HALF_UP);
    }

    public String inputString(){
        String input = sc.nextLine();
        sc.next();
        return input;
    }

    public String inputString(int size){
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

    public boolean inputBoolean(){
        int input = inputInt();
        try{
            if(input > 1 | input < 0){
                throw new InputMismatchException("Must be 1(True) or 0(False)!");
            }
        } catch(InputMismatchException e){
            System.err.println(e.getMessage());
        }
        return input == 1;
    }


}
