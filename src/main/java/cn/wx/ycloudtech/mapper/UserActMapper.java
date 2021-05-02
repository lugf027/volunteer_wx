package cn.wx.ycloudtech.mapper;

import cn.wx.ycloudtech.domain.UserAct;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "UserActMapper")
public interface UserActMapper extends BaseMapper<UserAct> {
}
