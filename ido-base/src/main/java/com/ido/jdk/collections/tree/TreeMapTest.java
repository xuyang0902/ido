package com.ido.jdk.collections.tree;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author xu.qiang
 * @date 19/9/18
 */
public class TreeMapTest {


    public static void main(String[] args) {

        TreeMap<Integer,String> treeMap = new TreeMap<Integer, String>();


        treeMap.put(1,"1");
        treeMap.put(2,"2");
        treeMap.put(3,"3");
        treeMap.put(4,"4");


        treeMap.remove(1);


    }
}
