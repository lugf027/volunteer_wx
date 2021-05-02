package cn.wx.ycloudtech.mapper;

import cn.wx.ycloudtech.domain.Organization;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "OrganizationMapper")
public interface OrganizationMapper extends BaseMapper<Organization> {
}
