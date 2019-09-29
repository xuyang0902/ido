package com.ido.rocketmq.store;

import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xu.qiang
 * @date 19/8/13
 */
public class 模拟rocketmq文件预热 {

    private static final Logger log = LoggerFactory.getLogger(模拟rocketmq文件预热.class);

    public static void main(String [] args) throws Exception {

        args = new String[]{"/Users/tbj/usr/local/tmp/rocketmq/yuren","true"};

        int fileSize = 1024 * 1024 * 1024 ;
        File file = new File(args[0]);
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, fileSize );

        System.out.println("step 0");
        Thread.sleep(1000 * 60);


        ByteBuffer byteBuffer = mappedByteBuffer.slice();
        if(args.length >1 && args[1]!=null && Boolean.parseBoolean(args[1])){
            for (int i = 0; i < 1024 * 1024 * 1024; i += 1024 * 4) {
                byteBuffer.put(i, (byte) 0);
            }
        }

        /**
         * 虽然对内存景行了预热，但是当内存不够的情况下，还是有概率被换出去的。
         * 所以要mlock
         */

        final long beginTime = System.currentTimeMillis();
        final long address = ((DirectBuffer) (mappedByteBuffer)).address();
        Pointer pointer = new Pointer(address);
        {
            /*
             * 实现是将锁住指定的内存区域避免被操作系统调到swap空间中。
             * OS在内存不够的情况下，会把内存swap到磁盘上，下次你又要访问这块数据的时候又要重新分配一块内存，然后从disk io读取到内存中，代价太大
             */
            int ret = LibC.INSTANCE.mlock(pointer, new NativeLong(fileSize));
            log.info("mlock {} {} {} ret = {} time consuming = {}", new Object[]{address, file.getName(), file.length(), ret, System.currentTimeMillis() - beginTime});

        }

        {
            /*
             * 实现是一次性先将一段数据读入到映射内存区域，这样就减少了缺页异常的产生。
             */
            int ret = LibC.INSTANCE.madvise(pointer, new NativeLong(fileSize), LibC.MADV_WILLNEED);
            log.info("mlock {} {} {} ret = {} time consuming = {}", new Object[]{address, file.getName(), file.length(), ret, System.currentTimeMillis() - beginTime});

        }


        System.out.println("step 1");
        Thread.sleep(1000* 10);


        mappedByteBuffer.force();
        System.out.println("step 2");
        Thread.sleep(1000* 10 * 100);
    }

}
