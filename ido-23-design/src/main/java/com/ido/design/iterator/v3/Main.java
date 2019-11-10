package com.ido.design.iterator.v3;



public class Main {
    public static void main(String[] args) {
        Collection_ list = new ArrayList_();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s"+i));
        }
        System.out.println(list.size());
    }
}
