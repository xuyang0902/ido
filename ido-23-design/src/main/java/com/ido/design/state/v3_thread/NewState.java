package com.ido.design.state.v3_thread;

public class NewState extends ThreadState_ {
    Thread_ t;
    public NewState(Thread_ t){
        this.t = t;
    }
    @Override
    void move(Action action) {
        if (action.msg == "start"){
            t.state = new RunningState(t);
            System.out.println("running..");
        }
    }

    @Override
    void run() {

    }
}
