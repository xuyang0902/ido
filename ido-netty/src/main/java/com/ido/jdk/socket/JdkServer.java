package com.ido.jdk.socket;


import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author xu.qiang
 * @date 19/11/5
 */
public class JdkServer {

    public static void main(String[] args) throws Exception {


        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 8080));

        while (true) {

            final Socket accept = serverSocket.accept();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    new ServerHandler(accept).handle();
                }
            }).start();

        }
    }


    static class ServerHandler {


        private Socket socket;

        public ServerHandler(Socket socket) {
            this.socket = socket;
        }


        public void handle() {


            try {
                InputStream inputStream = socket.getInputStream();

                byte[] data = new byte[1024];
                int len;
                while ((len = inputStream.read(data)) != -1) {

                    System.out.println("接收到客户端消息:" + new String(data, 0, len));
                    socket.getOutputStream().write("Pong".getBytes());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }


}
