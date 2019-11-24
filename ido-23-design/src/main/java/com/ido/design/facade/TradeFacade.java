package com.ido.design.facade;

/**
 *
 * 最少知识原则
 *
 * 对外的门面 尽量抽象 越少越好  越独立越好
 * @author xu.qiang
 * @date 2019/11/24
 */
public interface TradeFacade {

    Object getBuyToken(String userId);

    Object buy(Object buyParam);

}
