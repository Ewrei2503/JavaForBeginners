package by.yahor_kulesh.model;

import java.util.UUID;

public abstract class Data {
    protected final UUID id = UUID.randomUUID();

    public abstract UUID getId();

    public String print(){
        return "print content in console";
    }
}
