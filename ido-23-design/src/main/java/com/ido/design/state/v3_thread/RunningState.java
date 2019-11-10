package com.ido.design.state.v3_thread;

public class RunningState extends ThreadState_{
    private  Thread_ t;
    public RunningState(Thread_ t){
        this.t = t;
    }
    @Override
    void move(Action action) {
        if (action.msg == "running"){
            t.state = new TerminatedState(t);
            System.out.println("terminated..");
        }
    }

    @Override
    void run() {

    }
}
