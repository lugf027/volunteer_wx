package cn.wx.ycloudtech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UploadConfig uploadConfig;

    private static final Logger log = LoggerFactory.getLogger(WebMvcConfig.class);

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.warn("staticPath:\t" + uploadConfig.getStaticPath());
        log.warn("uploadPath:\t" + uploadConfig.getUploadPath());
        registry.addResourceHandler(uploadConfig.getStaticPath() + "/**")
                .addResourceLocations("file:" + uploadConfig.getUploadPath());
    }
}
