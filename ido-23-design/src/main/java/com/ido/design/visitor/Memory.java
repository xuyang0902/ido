package com.ido.design.visitor;

public class Memory implements ComputerPart {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitMemory(this);
    }

    @Override
    public double getPrice() {
        return 300;
    }
}
