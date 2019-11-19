package com.ido.javassist;

import com.sun.tools.attach.*;

import java.io.IOException;

/**
 * @author xu.qiang
 * @date 19/11/19
 */
public class TestAgent6 {

    public static void main(String[] args) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException, InterruptedException {

        // attach to target VM
        VirtualMachine vm = VirtualMachine.attach("23841");
        vm.loadAgent("/Users/tbj/.m2/repository/com/ido/ido-javaagent6/1.0/ido-javaagent6-1.0.jar");
        Thread.sleep(1000);
        vm.detach();


    }
}
