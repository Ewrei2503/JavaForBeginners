package by.yahor_kulesh.model;

import java.util.Objects;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.UUID;

public abstract class Data implements Printable{
    private final ZonedDateTime creationTime = ZonedDateTime.now();

    private UUID id = UUID.randomUUID();

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
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
