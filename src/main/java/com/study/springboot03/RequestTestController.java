package com.study.springboot03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: SpringBoot03
 * @description: request域请求
 * @author: yangyb
 * @create: 2021-11-27 21:46
 **/
@Controller
public class RequestTestController {

    @GetMapping("/goto")
    public String gotoPage(HttpServletRequest request){
        request.setAttribute("msg","消息成功了！");
        request.setAttribute("code",200);
        // 转发到/success请求
        return "forward:/success";
    }
    @ResponseBody
    @GetMapping("/success")
    public Map success(@RequestAttribute("msg" ) String msg,
                       @RequestAttribute("code") Integer code,
                       HttpServletRequest request){
        //通过请求会话获取
        Object attribute = request.getAttribute("msg");
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("reqMethod_msg",attribute);
        //通过注解获取
        hashMap.put("annotation_msg",msg);
        return hashMap;

    }

    /**
    * @Description: 矩阵变量的参数请求
     *
     *  请求：/cars/sell;low=34;brand=byd,audi,yd 【语法：路径中以分号";"分割】
     *  注：在使用中SpringBoot默认是禁用了矩阵变量的功能需要手动开启
     *  手动开启：
     *  原理==》对于路径的处理，UrlPathHelper进行解析。removeSemicolonContent = true(移除分号内容 )
    * @Param: []
    * @return:
    */
    @GetMapping("/cars/{path}")
    public Map carsSell(@MatrixVariable("low") Integer low,
                        @MatrixVariable("brand") List<String> list,
                        @PathVariable("path") String path){
        Map<String, Object> map = new HashMap<>();
        map.put("low",low);
        map.put("brand",list);
        map.put("path",path);

        return map;

    }
    //boss/1;age=20/2;age=10
    @GetMapping("/boss/{bossId}/{empId}")
    public Map boss(@MatrixVariable(value = "age", pathVar = "bossId") Integer bossAge,
                    @MatrixVariable(value = "age", pathVar = "empId") Integer empAge,
                    @PathVariable String bossId, @PathVariable String empId){
        Map<String, Object> map = new HashMap<>();
        map.put("bossAge",bossAge);
        map.put("empAge",empAge);
        return map;

    }


}
