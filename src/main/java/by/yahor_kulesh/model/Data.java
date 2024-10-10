package by.yahor_kulesh.model;

import java.util.UUID;

public abstract class Data implements Printable{
    private UUID id = UUID.randomUUID();

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                '}';
    }

}
