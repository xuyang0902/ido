package com.ido.jdk.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author xu.qiang
 * @date 19/8/15
 */
public class MappedByteBufferTest {


    public static void main(String[] args) throws IOException {


        RandomAccessFile rw = new RandomAccessFile("/Users/tbj/usr/local/tmp/java/java.txt", "rw");
        MappedByteBuffer mappedByteBuffer = rw.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, 1024*1024*100);

        ByteBuffer byteBuffer = mappedByteBuffer.slice();


        byteBuffer.putInt(1);
        byteBuffer.putInt(2);

        byteBuffer.position(4);
        int size =4;
        ByteBuffer byteBufferNew = byteBuffer.slice();
        byteBufferNew.limit(size);

        System.out.println(byteBufferNew.getInt());


        byteBuffer = mappedByteBuffer.slice();
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getInt());


    }
}
