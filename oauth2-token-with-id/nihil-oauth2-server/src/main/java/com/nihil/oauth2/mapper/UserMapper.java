package com.nihil.oauth2.mapper;

import com.nihil.common.auth.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findById(Long id);

    User findByUsername(String username);

    void save(User user);

    void update(User user);

    void deleteById(Long id);
}