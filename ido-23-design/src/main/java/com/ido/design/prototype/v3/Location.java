package com.ido.design.prototype.v3;

public class Location implements Cloneable {
    StringBuffer street;
    int roomNo;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Location(StringBuffer street, int roomNo){
        this.street = street;
        this.roomNo = roomNo;
    }
}
