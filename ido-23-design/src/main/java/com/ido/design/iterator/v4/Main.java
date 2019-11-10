package com.ido.design.iterator.v4;


public class Main {
    public static void main(String[] args) {
        Collection_ list = new ArrayList_();
        for (int i = 0; i < 15; i++) {
            list.add(new String("s"+i));
        }
        System.out.println(list.size());
        ArrayList_ al = (ArrayList_)list;
        for (int i = 0; i < al.size(); i++) {
            //get(i)方法
            //如果这种遍历方式,就不能实现通用了
        }
    }

}
