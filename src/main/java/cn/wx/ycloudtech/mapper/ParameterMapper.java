package cn.wx.ycloudtech.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import cn.wx.ycloudtech.domain.Parameter;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ParameterMapper")
public interface ParameterMapper extends BaseMapper<Parameter> {

}

