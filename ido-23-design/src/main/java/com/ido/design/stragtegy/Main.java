package com.ido.design.stragtegy;

import java.util.Arrays;

/**
 * Comparable
 * Compartor 策略模式
 * 开闭原则 :对修改关闭 对扩展开放
 */
public class Main {
    public static void main(String[] args) {
        //int[] arr = {1,2,3,4,4,3,5,0};
        Cat[] cats = {new Cat(1,1),new Cat(3,3),new Cat(0,0),new Cat(2,2)};
        //Dog[] dogs = {new Dog(2),new Dog(8),new Dog(4)};
        Sorter sorter = new Sorter();
        //sorter.sort(cats,new CatWeightComparator());
        sorter.sort(cats,new CatHeightComparator());
        System.out.println(Arrays.toString(cats));


    }
}
