package by.yahor_kulesh.mappers;

import org.mapstruct.Named;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class CommonMapper {
    @Named(value = "zonedDateTimeToTimestamp")
    protected Timestamp zonedDateTimeToTimestamp(ZonedDateTime zdt) {
        return Timestamp.valueOf(zdt.toLocalDateTime());
    }

    @Named(value = "timestampToZonedDateTime")
    protected  ZonedDateTime timestampToZonedDateTime(Timestamp ts) {
        return ts.toLocalDateTime().atZone(ZoneId.systemDefault());
    }
}
