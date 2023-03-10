package com.study.springboot03;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBoot03
 * @description: 参数传递
 * @author: yangyb
 * @create: 2021-11-27 15:44
 **/
@RestController
public class ParameterTestController {

    /**
    * @Description: 参数请求
     *
     * @PathVariable :获取路径变量参数的值【注：参数类型为Map时：路径上所有参数的值k-v】
     *
     * @RequestHeader: 获取请求头（指定参数）的值【注：参数类型为Map时：获取请求头中所有参数的值k-v】
     *
     * @RequestParam: 获取请求参数【注：参数类型为Map时：获取请求参数中所有的参数值】
     *
     * @CookieValue: 获取cookie值(根据参数获取指定的值)【注：参数类型为Cookie时：获取Cookie中使用的值】
     *
     * @RequestBody: 获取POST请求方式的请求体提交
     *
     * @RequestAttribute: 获取request域的请求的值
    * @Param: []
    * @return:
    */
    @GetMapping("/car/{id}/owner/{username}")
    public Map<String, Object> getCar(@PathVariable("id") Integer id ,
                                      @PathVariable("username") String name,
                                      @PathVariable Map<String,String> pv,
                                      @RequestHeader("User-Agent") String agentHeader,
                                      @RequestHeader Map<String,String> header,
                                      @RequestParam("age") Integer age,
                                      @RequestParam("inters")List inters,
                                      @RequestParam Map<String,String> reqParams){
        Map<String, Object> map = new HashMap<>();
        map.put("id",id);
        map.put("username",name);
        map.put("pv",pv);
        map.put("User-Agent",agentHeader);
        map.put("header",header);
        map.put("list",inters);
        map.put("reqParams",reqParams);
        map.put("age",age);

        return map;

    }
    @PostMapping("/save")
    public Map postMethod (@RequestBody String content){
        Map<String, Object> map = new HashMap<>();
        map.put("content",content);
        return map;
    }

}
