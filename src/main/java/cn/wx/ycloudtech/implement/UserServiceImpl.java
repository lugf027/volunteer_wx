package cn.wx.ycloudtech.implement;

import cn.wx.ycloudtech.domain.User;
import cn.wx.ycloudtech.mapper.UserMapper;
import cn.wx.ycloudtech.service.UserService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public User getUserByPwd(String userName, String pwd) {
        List<User> userList = userMapper.selectList(new EntityWrapper<User>()
                .eq("USER_NAME", userName)
                .eq("USER_PWD", pwd));
        if (userList.size() > 0) return userList.get(0);
        else return null;
    }

    @Override
    public int getUserCountByName(String userName) {
        return userMapper.selectCount(new EntityWrapper<User>()
                .eq("USER_NAME", userName));
    }
}
