package com.study.springboot03;

import org.springframework.web.bind.annotation.*;

/**
 * @program: SpringBoot03
 * @description: 请求处理-请求方式流程剖析
 * @author: yangyb
 * @create: 2021-11-23 20:10
 **/
@RestController
public class RequestController {
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String getUser() {

        return "GET-张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String saveUser() {
        return "POST-张三";
    }


    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public String putUser() {

        return "PUT-张三";
    }

    @RequestMapping(value = "/user", method = RequestMethod.DELETE)
    public String deleteUser() {
        return "DELETE-张三";
    }

    //扩展点：如何把 _method 这个名字换成我们自己喜欢的
}
