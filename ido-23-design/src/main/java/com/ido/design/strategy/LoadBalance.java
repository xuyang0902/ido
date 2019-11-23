package com.ido.design.strategy;

import java.util.List;

/**
 * 行为的抽象。
 * @author xu.qiang
 * @date 19/11/23
 */
public interface LoadBalance {


    public Integer select(List<Integer> list);

}
