package by.yahor_kulesh.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

@Setter
public abstract class Data implements Printable{
    private final ZonedDateTime objectCreationTime = ZonedDateTime.now();

    private ZonedDateTime creationTime = null;

    @Getter
    private UUID id = UUID.randomUUID();

    public ZonedDateTime getCreationTime() {
        return (Objects.isNull(creationTime))? objectCreationTime: creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Data data)) return false;
        return Objects.equals(id, data.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                '}';
    }

}
