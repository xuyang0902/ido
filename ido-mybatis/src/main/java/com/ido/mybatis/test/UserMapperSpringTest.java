package com.ido.mybatis.test;

import com.ido.mybatis.mapper.UserMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author xu.qiang
 * @date 18/12/5
 */
public class UserMapperSpringTest {


    public static void main(String[] args) {


        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("mybatis/spring-db.xml");


        final UserMapper userMapper = applicationContext.getBean(UserMapper.class);

        System.out.println("#######  " + userMapper.selectById(1L));
        System.out.println("#######  " + userMapper.selectById(1L));


        System.out.println("---------##############################------------");

        TransactionTemplate template = applicationContext.getBean("transactionTemplate", TransactionTemplate.class);


        template.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus transactionStatus) {

                System.out.println("####### in transaction " + userMapper.selectById(1L));
                System.out.println("####### in transaction " + userMapper.selectById(1L));
                return null;
            }
        });

    }

}
