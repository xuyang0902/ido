package com.ido.design.prototype.v2;

public class Location implements Cloneable {
    String street;
    int roomNo;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Location(String street, int roomNo){
        this.street = street;
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", roomNo=" + roomNo +
                '}';
    }
}
