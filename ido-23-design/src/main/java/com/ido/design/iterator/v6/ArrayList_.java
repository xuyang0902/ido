package com.ido.design.iterator.v6;


public class ArrayList_<E> implements Collection_<E> {
    E[] objects =(E[]) new Object[10];
    private int index = 0;
    public void add(E o){
        if (index ==objects.length){
            E[] newObjects =(E[]) new Object[objects.length * 2];
            System.arraycopy(objects,0,newObjects,0,objects.length);
            objects = newObjects;
        }
        objects[index] = o;
        index++;
    }
    public int size(){
        return index;
    }
    public Iterator_ iterator(){
        return new ArrayListIterator();
    };
    private class ArrayListIterator implements Iterator_ {
        int currentIndex = 0 ;
        @Override
        public boolean hasNext() {
            if (currentIndex >= index) return false;
            return true;
        }

        @Override
        public Object next() {
            Object o = objects[currentIndex];
            currentIndex++;
            return o;
        }
    }
}
