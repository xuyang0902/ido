package com.ido.design.visitor;

public class Cpu implements ComputerPart {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitCpu(this);
    }

    @Override
    public double getPrice() {
        return 500;
    }
}
