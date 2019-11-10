package com.ido.design.state.v3_thread;

public class TerminatedState extends ThreadState_{
    Thread_ t;
    public TerminatedState(Thread_ t){
        this.t = t;
    }
    @Override
    void move(Action action) {

    }

    @Override
    void run() {

    }
}
