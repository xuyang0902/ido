package com.ido.jdk.concurrent.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;


/**
 * 栅栏
 *
 *
 * 这个实现就更简单了
 *
 * 1、利用ReetrantLock和condition
 *
 *
 *
 * await的时候每次拿到lock后 count-- 然后就condition.await()
 * 等到count为0的时候 condition.signalAll()唤醒所有的condition 从条件队列到阻塞队列中去抢锁
 *
 *
 *
 */
public class CyclicBarrierTest {

    public static void main(String[] args){
		int N = 4;
		CyclicBarrier barrier = new CyclicBarrier(N);
		for(int i=0;i<N;i++){
			new Writer(barrier).start();
		}
	}
	
	static class Writer extends Thread{
		private CyclicBarrier cyclicBarrier;
		public Writer(CyclicBarrier cyclicBarrier){
			this.cyclicBarrier = cyclicBarrier;
		}

		@Override
		public void run(){
			System.out.println("线程"+Thread.currentThread().getName()+"正在写入数据...");
			try{
				Thread.sleep(5000);   //以睡眠来模拟写入数据操作
				System.out.println("线程"+Thread.currentThread().getName()+"写入数据完毕，等待其他线程写入完毕");
				cyclicBarrier.await();
			}catch(InterruptedException e){
				e.printStackTrace();
			}catch(BrokenBarrierException e){
				e.printStackTrace();
			}
			System.out.println("所有线程写入完毕，继续处理其他任务...");
		}
	}
}