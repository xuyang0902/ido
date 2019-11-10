package com.ido.design.iterator.v5;


public class Main {
    public static void main(String[] args) {
        Collection_ list = new LinkedList_();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s"+i));
        }
        System.out.println(list.size());

        Iterator_ iterator = list.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            System.out.println(((Node)next).getO());
        }
    }

}
