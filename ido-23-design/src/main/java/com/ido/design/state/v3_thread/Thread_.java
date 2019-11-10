package com.ido.design.state.v3_thread;

public class Thread_ {
    ThreadState_ state;
    void move(Action input){
        state.move(input);
    }
    void run(){
        state.run();
    }
}
