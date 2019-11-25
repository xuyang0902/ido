package com.ido.design.iterator;

/**
 * 迭代器接口
 * @author xu.qiang
 * @date 2019/11/24
 */
public interface Iterator<T> {

    boolean hashNext();

    T getNext();

}
