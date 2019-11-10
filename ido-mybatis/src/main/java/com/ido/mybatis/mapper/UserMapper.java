package com.ido.mybatis.mapper;

import com.ido.mybatis.model.User;
import org.mybatis.spring.annotation.MapperScan;

/**
 * @author xu.qiang
 * @date 18/12/5
 */
@MapperScan
public interface UserMapper {

    User selectById(Long id);

    int updateById(User user);

}
