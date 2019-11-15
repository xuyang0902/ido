package com.ido.sharding.dao;

import com.ido.sharding.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author xu.qiang
 * @date 19/11/15
 */
@Repository
public interface UserDao {

     @Insert("insert into `user` ( `id`,`age`, `name`, `create_time`, `modify_time`) values (#{id},#{age},#{name},#{createTime},#{modifyTime} )")
     void insert(@Param("id") Long id,
                 @Param("age") int age,
                 @Param("name") String name,
                 @Param("createTime") Date createTime,
                 @Param("modifyTime") Date modifyTIme);


     @Select("select * from user where id = #{id}")
     User selectById(@Param("id") Long id);



}
