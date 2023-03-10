package com.study.springboot03;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.util.UrlPathHelper;

/**
 * @program: SpringBoot03
 * @description: 自定义配置类
 * @author: yangyb
 * @create: 2021-11-27 22:37
 **/
@Configuration(proxyBeanMethods = false)
public class WebConfig implements WebMvcConfigurer {
    /**
    * @Description: 方法二
    * @Param: []
    * @return: 
    */
/*    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void configurePathMatch(PathMatchConfigurer configurer) {
                UrlPathHelper pathHelper = new UrlPathHelper();
                //设置为不移除;后面的内容。矩阵变量功能就可以生效
                pathHelper.setRemoveSemicolonContent(false);
                configurer.setUrlPathHelper(pathHelper);
            }
        };
    }*/
    /**
    * @Description: 方法一：
    * @Param: [configurer]
    * @return: 
    */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        UrlPathHelper pathHelper = new UrlPathHelper();
        //设置为不移除;后面的内容。矩阵变量功能就可以生效
        pathHelper.setRemoveSemicolonContent(false);
        configurer.setUrlPathHelper(pathHelper);
    }
}
