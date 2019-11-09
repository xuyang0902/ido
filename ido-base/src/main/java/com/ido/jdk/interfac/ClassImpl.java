package com.ido.jdk.interfac;

/**
 * @author xu.qiang
 * @date 19/10/29
 */
public class ClassImpl implements IClassA,IClassB {
    @Override
    public int getAge() {
        return 0;
    }


    public static void main(String[] args) {


        IClassA classA = new ClassImpl();

        int age = classA.getAge();
        System.out.println(age);


        int age1 = ((IClassB) classA).getAge();

        System.out.println(age1);
    }
}
