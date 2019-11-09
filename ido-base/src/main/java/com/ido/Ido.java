package com.ido;

import sun.jvm.hotspot.debugger.cdbg.AccessControl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * ido
 *
 * @author xu.qiang
 * @date 19/4/25
 */
public class Ido implements Cloneable{

    public static void main(String[] args) throws InterruptedException, IOException {


        System.out.println( 2<< 9);


    }

}
