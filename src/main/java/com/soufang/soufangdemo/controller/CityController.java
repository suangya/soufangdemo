package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private static List<String> cities = new ArrayList<>();

    static {
        cities.add("成都");
        cities.add("广州");
        cities.add("武汉");
    }

    @GetMapping
    public ApiResponse getAll(HttpServletResponse response) {
        return ApiResponse.success(cities);
    }
}
