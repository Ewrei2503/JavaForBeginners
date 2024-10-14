package by.yahor_kulesh.model.users;

import by.yahor_kulesh.model.Data;

public abstract class User extends Data{
    public String printRole(){
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
