package com.ido.popular.rocketmq;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xu.qiang
 * @date 19/8/13
 */
public class TestFile {

    public static void main(String [] args) throws Exception {
        File file = new File(args[0]);
        FileChannel fileChannel = new RandomAccessFile(file, "rw").getChannel();
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, 1024 * 1024 * 1024);
        if(args.length >1 && args[1]!=null && Boolean.parseBoolean(args[1])){
            for (int i = 0, j = 0; i < 1024 * 1024 * 1024; i += 1024 * 4, j++) {
                mappedByteBuffer.put(i, (byte) 0);
            }
        }
        Thread.sleep(1000000000);
    }

}
