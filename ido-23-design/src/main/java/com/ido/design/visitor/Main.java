package com.ido.design.visitor;

public class Main {
    public static void main(String[] args) {
        CorpVisitor visitor = new CorpVisitor();
        new Computer().accept(visitor);
        System.out.println(visitor.totalPrice);
    }
}
