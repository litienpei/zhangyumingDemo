package org.opens.mybatisplus.service.impl;

import org.opens.mybatisplus.dao.UserMapper;
import org.opens.mybatisplus.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public boolean removeById(int id) {
        int affectedRows = userMapper.deleteById(id);
        if(id == 65538) {
            userMapper.deleteById(8);
            double a = 1 / 0;
        }
        return affectedRows == 1;
    }

}
