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

    public String printRole(){
        return this.getClass().getSimpleName();
    }
}
