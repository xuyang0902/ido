package com.ido.mybatis.test;

import com.ido.mybatis.mapper.UserMapper;
import com.ido.mybatis.model.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;

/**
 * @author xu.qiang
 * @date 18/12/5
 */
public class UserMapperTest {

    private SqlSessionFactory sqlSessionFactory;
    private Reader reader;

    @Before
    public void init() {
        try {
            reader = Resources.getResourceAsReader("mybatis/mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

            System.out.println("本地缓存范围: " + sqlSessionFactory.getConfiguration().getLocalCacheScope());
            System.out.println("二级缓存是否被启用: " + sqlSessionFactory.getConfiguration().isCacheEnabled());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCacheOne() {
        SqlSession session = sqlSessionFactory.openSession(true);
        SqlSession session2 = sqlSessionFactory.openSession(true);

        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserMapper mapper2 = session2.getMapper(UserMapper.class);
            User user = mapper.selectById(1L);
            System.out.println("#############" + user);
            User user2 = mapper2.selectById(1L);
            System.out.println("#############2" + user2);

            user.setAge(100);
            int i = mapper.updateById(user);

            user = mapper.selectById(1L);
            System.out.println("#############" + user);
            user2 = mapper2.selectById(1L);//一级缓存session会导致脏读
            System.out.println("#############2" + user2);


        } finally {
            session.close();
        }
    }


    @Test
    public void testCacheTwo() {
        SqlSession session = sqlSessionFactory.openSession(true);
        SqlSession session2 = sqlSessionFactory.openSession(true);

        try {
            UserMapper mapper = session.getMapper(UserMapper.class);
            UserMapper mapper2 = session2.getMapper(UserMapper.class);


            User user = mapper.selectById(1L);
            System.out.println("#############" + user);
            session.commit();

            User user2 = mapper2.selectById(1L);
            System.out.println("#############2" + user2);

        } finally {
            session.close();
        }
    }

}
