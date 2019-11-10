package com.ido.design.command;

public class InsertCommand extends Command{
    Content content;
    String insertMsg = "http://www.mashibing.com";
    public InsertCommand(Content content){
        this.content = content;
    }
    @Override
    public void doit() {
        content.msg = content.msg + insertMsg;
    }

    @Override
    public void undo() {
        content.msg = content.msg.substring(0,content.msg.length() - insertMsg.length());
    }
}
