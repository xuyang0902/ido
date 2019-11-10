package com.ido.design.command;

public class Main {
    public static void main(String[] args) {
        Content content = new Content();
        Command command = new DeleteCommand(content);
        command.doit();
        System.out.println(content.msg);
        command.undo();
        System.out.println(content.msg);
    }
}
