package by.yahor_kulesh;

import by.yahor_kulesh.model.Data;

import java.util.UUID;


public class App extends Data {

    public static void main(String[] args) {

    }

    /*private static void menu() {
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
                    System.out.println(new Ticket());
                    break;
                }
                case 2: {
                    System.out.println(TicketService.createLimitedTicket());
                    break;
                }
                case 3: {
                    System.out.println(TicketService.createFullTicket());
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
    }*/


    @Override
    public UUID getId() {
        return super.id;
    }
}
