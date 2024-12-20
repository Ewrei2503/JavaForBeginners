package by.yahor_kulesh.mappers;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.mapstruct.Named;

public interface CommonMapper {
  @Named(value = "zonedDateTimeToTimestamp")
  default Timestamp zonedDateTimeToTimestamp(ZonedDateTime zdt) {
    return Timestamp.valueOf(zdt.toLocalDateTime());
  }

  @Named(value = "timestampToZonedDateTime")
  default ZonedDateTime timestampToZonedDateTime(Timestamp ts) {
    return ts.toLocalDateTime().atZone(ZoneId.systemDefault());
  }
}
