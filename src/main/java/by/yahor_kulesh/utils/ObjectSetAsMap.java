package by.yahor_kulesh.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Consumer;

public class ObjectSetAsMap {

    private static final Object PRESENT = new Object();

    private final HashMap<Object,Object> objects;


    public ObjectSetAsMap() {
        objects = new HashMap<>();
    }

    public boolean add(Object o) {
        return objects.put(o, PRESENT)==null;
    }

    public boolean contains(Object o) {
        return objects.containsKey(o);
    }

    public boolean remove(Object o) {
        return objects.remove(o)==null;
    }

    public void iterate(Consumer<? super Object> action) {
        objects.keySet().iterator().forEachRemaining(action);
    }

    public Iterator<Object> iterate() {
        return objects.keySet().iterator();
    }

}
