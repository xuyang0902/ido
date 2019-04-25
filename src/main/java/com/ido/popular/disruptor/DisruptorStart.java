package com.ido.popular.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.UUID;
import java.util.concurrent.Executors;


/**
 * disruptor
 *
 * @author xu.qiang
 * @date 18/7/5
 */
public class DisruptorStart {

    public static void main(String[] args) throws InterruptedException {

        //环形大小 必须是2的幂次  位运算，计算机效率高
        int ringBufferSize = 2 << 0;

        //disruptor
        Disruptor<ValueEvent> disruptor = new Disruptor<ValueEvent>(ValueEvent.EVENT_FACTORY, ringBufferSize,
                Executors.defaultThreadFactory(), ProducerType.SINGLE, new BlockingWaitStrategy());

        //消费端事件处理器
        EventHandler<ValueEvent> eventHandler = new EventHandler<ValueEvent>() {

            @Override
            public void onEvent(final ValueEvent event, final long sequence, final boolean endOfBatch) throws Exception {
                System.out.println("Sequence: " + sequence);
                System.out.println("ValueEvent: " + event.getValue());
            }
        };

        disruptor.handleEventsWith(eventHandler);

        //初始化disruptor 启动消费线程
        RingBuffer<ValueEvent> ringBuffer = disruptor.start();

        Thread.sleep(3000L);
        //生产端事件
        for (long i = 10; i < 11; i++) {
            String uuid = UUID.randomUUID().toString();
            // 取可以生产的坑位
            long index = ringBuffer.next();
            ValueEvent valueEvent = ringBuffer.get(index);
            valueEvent.setValue(uuid);
            //发布坑位
            ringBuffer.publish(index);
        }

        //
        disruptor.shutdown();

    }
}