package com.ido.sharding.main.spring;

import com.ido.sharding.dao.UserDao;
import com.ido.sharding.model.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * @author xu.qiang
 * @date 19/11/15
 */
public class ShardingJdbcMybatisMain {


    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/spring/sharding-jdbc-mybatis.xml");

        UserDao bean = applicationContext.getBean(UserDao.class);


        for (int index = 2; index < 1000; index++) {
            bean.insert((long) index, index, "yuren" + index, new Date(), null);
        }


        User user = bean.selectById(1L);
        System.out.println("###### --> " + user.toString());
    }


}
