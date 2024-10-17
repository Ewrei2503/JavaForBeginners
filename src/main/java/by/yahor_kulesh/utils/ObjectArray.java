package by.yahor_kulesh.utils;


public class ObjectArray {
    private Object[] objects;

    private int firstEmptyElement = 0;

    public ObjectArray() {
        objects = new Object[10];
    }


    public void add(Object object) {
        if(firstEmptyElement < objects.length) {
            objects[firstEmptyElement] = object;
            firstEmptyElement++;
        } else {
            objects = addAndResize(object);
        }
    }

    private Object[] addAndResize(Object object){
        Object[] resizedArray = new Object[objects.length + (objects.length>>1)];
        fillResizedArray(resizedArray, object);
        return resizedArray;
    }

    private void fillResizedArray(Object[] resizedArray,Object object) {
        int x=0;
        while(x<firstEmptyElement){
            resizedArray[x] = objects[x];
            x++;
        }
        resizedArray[x] = object;
    }

    public Object getByIndex(int index) {
        if(index < 0 || index >= firstEmptyElement) {
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. TicketRepo's last index is " + (firstEmptyElement-1));
        }
        return objects[index];
    }

    public void removeByIndex(int index) {
        if(index>=firstEmptyElement || index<0){
            throw new IndexOutOfBoundsException("Index " + index + " is out of bounds. TicketRepo's last index is " + (firstEmptyElement-1));
        } else {
            while(index<objects.length-2){
                objects[index] = objects[index+1];
                index++;
            }
            firstEmptyElement--;
        }
    }

    public int size() {
        return firstEmptyElement;
    }
}
