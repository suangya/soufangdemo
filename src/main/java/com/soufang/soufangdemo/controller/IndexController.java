package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class IndexController {
    @GetMapping("/")
    @PreAuthorize("hasAnyAuthority('admin','user')")
    public ApiResponse index(HttpServletRequest req){
        Map<String , String> result = new HashMap<>();
        /*return "hello,world！";*/
        result.put("hello","world!");
        return ApiResponse.success(result);
    }

    /*@RequestMapping("/hello")
    public String hello(HttpServletRequest request){
        return "hello:"+request.getParameter("name")+"!";
    }*/

    /*@RequestMapping("/info/{id}")
    @ResponseBody
    public String info(@PathVariable("id") String id){
        return "hello:"+id;
    }

    @RequestMapping("/{organ}/{project}") // ant 风格路径表达式
    public String hello2(@PathVariable("organ") String organ,
                         @PathVariable("project") String project) {
        return "hello ,team :" + organ + ", project: " + project;
    }*/
}
