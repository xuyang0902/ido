package com.ido.mybatis.mapper;

import com.ido.mybatis.model.User;

/**
 * @author xu.qiang
 * @date 18/12/5
 */
public interface UserMapper {

    User selectById(Long id);

    int updateById(User user);

}
