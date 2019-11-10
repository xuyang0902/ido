package com.ido.design.visitor;

public interface Visitor {
    void visitCpu(Cpu cpu);
    void visitMemory(Memory memory);
    void visitBoard(Board board);
}
