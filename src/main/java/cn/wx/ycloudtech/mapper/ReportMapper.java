package cn.wx.ycloudtech.mapper;

import cn.wx.ycloudtech.domain.Report;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "ReportMapper")
public interface ReportMapper extends BaseMapper<Report> {
}
