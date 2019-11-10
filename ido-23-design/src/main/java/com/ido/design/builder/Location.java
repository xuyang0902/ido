package com.ido.design.builder;

public class Location {
    String street;
    String roomNo;

    @Override
    public String toString() {
        return "Location{" +
                "street='" + street + '\'' +
                ", roomNo='" + roomNo + '\'' +
                '}';
    }

    public Location(String street, String roomNo){
        this.street = street;
        this.roomNo = roomNo;
    }
}
