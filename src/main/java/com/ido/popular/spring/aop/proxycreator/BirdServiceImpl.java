package com.ido.popular.spring.aop.proxycreator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xu.qiang
 * @date 19/5/5
 */
@Component
public class BirdServiceImpl implements BirdService {

    private static final Logger logger = LoggerFactory.getLogger(BirdService.class);

    @Override
    public void fly(String name) {

        logger.info("  name:{} 飞了哦", name);
    }
}
