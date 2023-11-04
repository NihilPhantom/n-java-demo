package com.nihil.oauth2.service;

import com.nihil.common.auth.User;
import com.nihil.oauth2.JOPO.IDHolderUserDetails;
import com.nihil.oauth2.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(Long id) {
        return userMapper.findById(id);
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public void save(User user) {
        userMapper.save(user);
    }

    public void update(User user) {
        userMapper.update(user);
    }

    public void deleteById(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println(username + "--" + "使用自定登录逻辑");

        // 根据用户名获取用户信息
        User authUser = userMapper.findByUsername(username);

        // 构造用户信息
        if(authUser != null){
            System.out.println(passwordEncoder.encode(authUser.getPassword()));
            List<GrantedAuthority> pluginRoleList = new ArrayList<>();
            return new IDHolderUserDetails(
                    authUser.getId().toString(),
                    authUser.getUsername(),
                    passwordEncoder.encode(authUser.getPassword()),
                    pluginRoleList
            );
        }

        throw new UsernameNotFoundException("用户不存在");
    }
}