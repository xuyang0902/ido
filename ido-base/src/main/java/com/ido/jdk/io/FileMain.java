package com.ido.jdk.io;

import java.io.*;

/**
 * @author xu.qiang
 * @date 19/8/15
 */
public class FileMain {

    public static void main(String[] args) throws IOException {


        System.out.println(System.getProperty("user.home"));

        String path = System.getProperty("user.home") + "/usr/local/tmp/java";


        File file = new File(path + "/java.txt");


        if (!file.exists()) {
            boolean newFile = file.createNewFile();

            if (!newFile) {
                throw new RuntimeException("创建文件异常");
            }

            FileOutputStream os = new FileOutputStream(file);
            os.write("hello file".getBytes("UTF-8"));
            os.flush();

            if (os != null) {
                os.close();
            }

        } else {


            FileInputStream is = new FileInputStream(file);

            int available = is.available();
            byte[] h = new byte[available];
            is.read(h);

            System.out.println(new String(h,"UTF-8"));
        }


    }
}
