package com.ido.design.iterator.v6;


public class Main {
    public static void main(String[] args) {
        Collection_<String> list = new LinkedList_<String>();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s"+i));
        }
        System.out.println(list.size());

        Iterator_<Node> iterator = list.iterator();
        while (iterator.hasNext()){
            Node next = iterator.next();
            System.out.println(next.getO());
        }
    }

}
