package com.ido.design.state.v3_thread;

public class Main {
    public static void main(String[] args) {
        ThreadState_ state = new RunningState(new Thread_());
        state.move(new Action("running"));
    }
}
