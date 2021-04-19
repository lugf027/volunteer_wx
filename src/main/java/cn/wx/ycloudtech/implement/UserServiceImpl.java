package cn.wx.ycloudtech.implement;

import cn.wx.ycloudtech.domain.User;
import cn.wx.ycloudtech.mapper.UserMapper;
import cn.wx.ycloudtech.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(String openId) {
        return userMapper.selectById(openId);
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int updateUser(User user) {
        return userMapper.updateById(user);
    }
}
