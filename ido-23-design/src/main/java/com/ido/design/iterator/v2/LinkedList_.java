package com.ido.design.iterator.v2;



public class LinkedList_ {
    Node head = null;
    Node tail = null;
    private int size = 0;
    public void add(Object o){
        Node node = new Node(o);
        node.next = null;
        if (head == null){
            head = node;
            tail = node;
        }
        tail.next = node;
        tail = node;
        size++;
    }
    public int size(){
        return size;
    }
}
