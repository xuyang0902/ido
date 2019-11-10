package com.ido.design.adapter;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.io.*;

public class AdapterTest3 {
    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream("D:/temp/123.txt");
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);
        String line = null;
        while ((line=br.readLine()) !=null && !line.equals("")){
            System.out.println(line);
        }
        br.close();
    }
}
