package by.yahor_kulesh.model;

public interface Printable {
    default String print(){
        return this.toString();
    }
}
