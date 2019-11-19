package com.ido.agent;


import java.lang.instrument.Instrumentation;


/**
 * 在main方法之前 Instrumentation 可以对class做自己想做的操作
 * <p>
 * java -javaagent:/path/to/agent.jar your.main.Clazz
 *
 * @author xu.qiang
 * @date 2018-11-07
 */
public class PreMainAgent {

    public static void premain(String args, Instrumentation instrumentation) {
        System.out.println("====premain方法执行1==== args:" + args);
        instrumentation.addTransformer(new Transformer());
    }


    /**
     * 如果不存在 premain(String agentOps, Instrumentation inst)
     * 则会执行 premain(String agentOps)
     */
    public static void premain(String agentOps) {
        System.out.println("====premain方法执行2====  args:" + agentOps);
    }


    public static void main(String[] args) {
        System.out.println("====premain方法执行3====");
    }

}