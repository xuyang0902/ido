package com.ido.jdk.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.channels.SocketChannel;

/**
 * @author xu.qiang
 * @date 19/11/5
 */
public class JdkClient {

    public static void main(String[] args) throws IOException, InterruptedException {

        final Socket socket = new Socket("127.0.0.1",8080);



        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        socket.getOutputStream().write("Ping".getBytes());
                        Thread.sleep(1000*5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();


        while(true){
            Thread.sleep(10000);
        }

    }
}
