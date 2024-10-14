package by.yahor_kulesh.repositories;

import by.yahor_kulesh.model.users.User;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class UserRepo {

    private static final Object PRESENT = new Object();

    private final HashMap<User,Object> users;


    public UserRepo() {
        users = new HashMap<>();
    }

    public boolean add(User user) {
        return users.put(user, PRESENT)==null;
    }

    public boolean contains(User user) {
        return users.containsKey(user);
    }

    public boolean remove(User user) {
        return users.remove(user)==null;
    }

    public void iterate(Consumer<? super User> action) {
        users.keySet().iterator().forEachRemaining(action);
    }

    public Iterator<User> iterate() {
        return users.keySet().iterator();
    }

}
