package cn.wx.ycloudtech.mapper;

import cn.wx.ycloudtech.domain.File;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "FileMapper")
public interface FileMapper extends BaseMapper<File> {
}
