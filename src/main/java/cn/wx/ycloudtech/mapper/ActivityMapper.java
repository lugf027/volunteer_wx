package cn.wx.ycloudtech.mapper;

import cn.wx.ycloudtech.domain.Activity;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ActivityMapper")
public interface ActivityMapper extends BaseMapper<Activity> {
}
