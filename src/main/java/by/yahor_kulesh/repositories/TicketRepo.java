package by.yahor_kulesh.repositories;

import by.yahor_kulesh.model.tickets.Ticket;


public class TicketRepo {
    private Ticket[] tickets;

    private int firstEmptyElement = 0;

    public TicketRepo() {
        tickets = new Ticket[10];
    }


    public void add(Ticket ticket) {
        if(firstEmptyElement < tickets.length) {
            tickets[firstEmptyElement] = ticket;
            firstEmptyElement++;
        } else {
            tickets = addAndResize(ticket);
        }
    }

    private Ticket[] addAndResize(Ticket ticket){
        Ticket[] resizedArray = new Ticket[tickets.length + (tickets.length>>1)];
        fillResizedArray(resizedArray, ticket);
        return resizedArray;
    }

    private void fillResizedArray(Ticket[] resizedArray,Ticket ticket) {
        int x=0;
        while(x<firstEmptyElement){
            resizedArray[x] = tickets[x];
            x++;
        }
        resizedArray[x] = ticket;
    }

    public Ticket getByIndex(int index) {
        if(index < 0 || index >= firstEmptyElement) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. TicketRepo's last index is " + (firstEmptyElement-1));
        }
        return tickets[index];
    }

    public void removeByIndex(int index) {
        if(index>=firstEmptyElement || index<0){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. TicketRepo's last index is " + (firstEmptyElement-1));
        } else {
            while(index<tickets.length-2){
                tickets[index] = tickets[index+1];
                index++;
            }
            firstEmptyElement--;
        }

    }

    public int size() {
        return firstEmptyElement;
    }
}
