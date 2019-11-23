package com.ido.design.singleton;

/**
 *
 * 枚举实现单列
 * @author xu.qiang
 * @date 19/11/23
 */
public enum  Singleton04 {

    INSTANCE1,INSTANCE2;

    public static Singleton04 getInstance1(){
        return Singleton04.INSTANCE1;
    }

}
