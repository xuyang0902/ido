package com.ido.jdk.net;

import java.net.*;
import java.util.Enumeration;

/**
 * @author xu.qiang
 * @date 19/8/19
 */
public class NetUtilTest {

    public static void main(String[] args) throws SocketException, UnknownHostException {


        Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
        while (enumeration.hasMoreElements()) {
            final NetworkInterface networkInterface = enumeration.nextElement();
            final Enumeration<InetAddress> en = networkInterface.getInetAddresses();
            while (en.hasMoreElements()) {
                final InetAddress address = en.nextElement();
                if (!address.isLoopbackAddress()) {

                    System.out.println(networkInterface.getName());
                    System.out.println(address.getHostName());

                    System.out.println();
                }
            }
        }


    }

}
