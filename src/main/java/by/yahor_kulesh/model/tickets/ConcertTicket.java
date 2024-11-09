package by.yahor_kulesh.model.tickets;

import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.UUID;

@Getter
public class ConcertTicket extends Ticket {

    private final String concertHall;
    private final String eventCode;
    private final boolean isPromo;

    private Sector sector;




    public ConcertTicket() {
        super();
        this.concertHall = "";
        this.eventCode = "";
        this.isPromo = false;
        this.sector = null;
    }

    public ConcertTicket(UUID id) {
        super(id);
        this.concertHall = "";
        this.eventCode = "";
        this.isPromo = false;
        this.sector = null;
    }

    public ConcertTicket(Ticket ticket) {
        super(ticket);
        this.concertHall = "";
        this.eventCode = "";
        this.isPromo = false;
        this.sector = null;
    }

    public ConcertTicket(Ticket ticket, String concertHall, int eventCode, boolean isPromo, String sector) {
        super(ticket);
        this.concertHall = validateStringLimits(concertHall, "Concert Hall", new char[][]{{'A','Z'}, {'a','z'}});
        this.eventCode = validateEventCode(eventCode);
        this.isPromo = isPromo;
        setSector(sector);
    }

    public ConcertTicket(String concertHall, int eventCode, boolean isPromo, String sector) {
        super();
        this.concertHall = validateStringLimits(concertHall, "Concert Hall", new char[][]{{'A','Z'}, {'a','z'}});
        this.eventCode = validateEventCode(eventCode);
        this.isPromo = isPromo;
        setSector(sector);
    }


    public void setSector(String sector) {
        this.sector = Sector.valueOf(validateStringLimits(sector,"Sector" ,new char[][]{{'A','C'}, {'a','c'}}));
    }

    public boolean isPromo() {
        return isPromo;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ConcertTicket that)) return false;
        if (!super.equals(o)) return false;
        return isPromo == that.isPromo && Objects.equals(concertHall, that.concertHall) && Objects.equals(eventCode, that.eventCode) && sector == that.sector;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), concertHall, eventCode, isPromo, sector);
    }

    @Override
    public String toString() {
        return "Concert ticket Info:\n" +
                       "ID: " + this.getId() +
                       ";\nUser's ID: " + this.getUserId() +
                       ";\nConcert Hall: " + this.getConcertHall() +
                       ";\nEvent Code: " + this.getEventCode() +
                       ";\nDate of event: " + ((Objects.isNull(this.getDate()))? null: this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                       ";\nPromo ticket: " + this.isPromo() +
                       ";\nSector: " + this.getSector() +
                       ";\nWas bought: " + ((Objects.isNull(this.getCreationTime()))? null: this.getCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                       ";\nPrice: " + (Objects.isNull(this.getPrice())?0.0:this.getPrice()) +
                       "$.\n\n\n";
    }


    @Override
    public void share(String email) {
        System.out.println("ConcertTicket was send to the next email: " + email + "\n");
    }

    public void share(String email, String phoneNumber) {
        System.out.println("ConcertTicket was send to the next email - " + email + ", and phone - " + phoneNumber + "\n");
    }
}
