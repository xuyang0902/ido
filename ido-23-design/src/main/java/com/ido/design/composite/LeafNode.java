package com.ido.design.composite;

public class LeafNode extends Node {
    String content;
    public LeafNode(String content){
        this.content = content;
    }
    @Override
    public void p() {
        System.out.println(content);
    }
}
