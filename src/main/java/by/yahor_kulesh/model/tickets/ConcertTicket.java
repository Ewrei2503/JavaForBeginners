package by.yahor_kulesh.model.tickets;

import java.time.format.DateTimeFormatter;

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

    public String getConcertHall() {
        return concertHall;
    }

    public String getEventCode() {
        return eventCode;
    }

    public boolean isPromo() {
        return isPromo;
    }

    public Sector getSector() {
        return sector;
    }

    @Override
    public String toString() {
        return "ConcertTicket{" +
                "concertHall='" + concertHall + '\'' +
                ", eventCode='" + eventCode + '\'' +
                ", isPromo=" + isPromo +
                ", sector=" + sector +
                "} " + super.toString();
    }

    @Override
    public String print() {
        return "Concert ticket Info:\n" +
                "ID: " + this.getId() +
                ";\nConcert Hall: " + this.getConcertHall() +
                ";\nEvent Code: " + this.getEventCode() +
                ";\nDate of event: " + (this.getDate() == null? null: this.getDate().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nPromo ticket: " + this.isPromo() +
                ";\nSector: " + this.getSector() +
                ";\nWas bought: " + (this.getTicketCreationTime() == null? null: this.getTicketCreationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME)) +
                ";\nPrice: " + (this.getPrice()==null?0.0:this.getPrice()) +
                "$.\n\n\n";
    }
}
