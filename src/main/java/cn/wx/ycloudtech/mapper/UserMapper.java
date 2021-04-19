package cn.wx.ycloudtech.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.wx.ycloudtech.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "UserMapper")
public interface UserMapper extends BaseMapper<User> {

}

