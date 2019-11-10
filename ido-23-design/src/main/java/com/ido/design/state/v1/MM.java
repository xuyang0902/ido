package com.ido.design.state.v1;

import org.junit.Test;

public class MM {
    MMState name;
    private enum MMState {HAPPY , SAD}
    @Test
    public void smile(){
        name = MMState.HAPPY;
        //switch case
        switch (name) {
            case HAPPY :
                System.out.println("HAPPY");
            break;

        }
    }
    @Test
    public void cry(){
        name = MMState.SAD;
        //switch case
        switch (name){
            case SAD :
                System.out.println("SAD");
            break;
        }
    }
    @Test
    public void say(){
        name = MMState.SAD;
        //switch case
        switch (name){
            case SAD :
                System.out.println("I`m sad");
                break;
        }
    }
}
