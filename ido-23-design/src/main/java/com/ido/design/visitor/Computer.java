package com.ido.design.visitor;

public class Computer {
    ComputerPart cpu = new Cpu();
    ComputerPart memory = new Memory();
    ComputerPart board = new Board();
    public void accept(Visitor visitor){
        this.cpu.accept(visitor);
        this.memory.accept(visitor);
        this.board.accept(visitor);
    }
}
