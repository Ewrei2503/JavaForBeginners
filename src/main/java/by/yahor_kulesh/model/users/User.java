package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.Data;

public abstract class User extends Data{
    private String name = "default_user";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printRole(){
        System.out.println(getRole());
    }

    public String getRole(){
        return this.getClass().getSimpleName();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
