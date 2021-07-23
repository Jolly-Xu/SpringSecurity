package com.xujialin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XuJiaLin
 * @date 2021/7/17 18:02
 */
@Controller
public class logincontroller {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/login")
    public String login(){
        return "login";
    }



    @RequestMapping("/index")
    public String index(){
        return "index";
    }

    @ResponseBody
    @PostMapping("/Error")
    public String Error(){return "Error";}

    @ResponseBody
    @RequestMapping("/vip")
    public String toVip(){
        Map<String,String> map=new HashMap<>();
        map.put("username","xujialin");
        //将数据存入redis
        redisTemplate.opsForHash().putAll("k1",map);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/vip2")
    public String tovop2(){
        redisTemplate.opsForHash().put("k1","username","cs");
        return "欢迎来到vip2页面";}


    @ResponseBody
    @RequestMapping("/vip3")
    public Object tovop3(){
        Object k1 = redisTemplate.opsForValue().get("k3");
        return k1;
    }


    @ResponseBody
    @RequestMapping("/vip4")
    public Object tovop4(){
        Object k1 = redisTemplate.opsForValue().get("k2");
        return k1;}


    @PreAuthorize("hasRole('Admin') ")
    @ResponseBody
    @RequestMapping("/vip5")
    public Object tovop5(){
        Object k1 = redisTemplate.opsForValue().get("k1");
        return k1;
    }

}
