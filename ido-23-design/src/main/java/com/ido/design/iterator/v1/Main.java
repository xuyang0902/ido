package com.ido.design.iterator.v1;

import com.ido.design.iterator.v2.LinkedList_;

public class Main {
    public static void main(String[] args) {
        ArrayList_ list = new ArrayList_();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s"+i));
        }
        System.out.println(list.size());
    }
}
