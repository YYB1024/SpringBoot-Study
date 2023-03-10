package com.study.springboot03;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @program: SpringBoot03
 * @description: 模板视图跳转
 * @author: yangyb
 * @create: 2021-12-01 20:15
 **/
@Controller
public class ViewTestController {
    @GetMapping("/view")
    public String view(Model model){
        // model中的数据会被放在请求域中request.setAttribute
        model.addAttribute("msg","你好");
        model.addAttribute("link","https://www.baidu.com");
        return "success";
    }

}
