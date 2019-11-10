package com.ido.design.visitor;

public class Board implements ComputerPart {
    @Override
    public void accept(Visitor visitor) {
        visitor.visitBoard(this);
    }

    @Override
    public double getPrice() {
        return 200;
    }
}
