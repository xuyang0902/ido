package com.ido.design.visitor;

public class CorpVisitor implements Visitor {
    double totalPrice = 0.0;
    @Override
    public void visitCpu(Cpu cpu) {
        totalPrice += cpu.getPrice()*0.8;
    }

    @Override
    public void visitMemory(Memory memory) {
        totalPrice += memory.getPrice()*0.7;
    }

    @Override
    public void visitBoard(Board board) {
        totalPrice += board.getPrice()*0.6;
    }
}
