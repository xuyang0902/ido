package com.ido.design.iterator.v5;

public class Node {
    Object o;
    Node next;
    public Node(Object o){
        this.o= o;
    }

    public Object getO() {
        return o;
    }

    public void setO(Object o) {
        this.o = o;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}
