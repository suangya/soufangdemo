package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.entity.Subway;
import com.soufang.soufangdemo.repository.SubwayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subways")
public class SubwayController {

    private SubwayRepository subwayRepository;

    // spring boot 框架自动注入 SubwayRepository 实例
    // constructor 注入拥有更好的可测试性
    public SubwayController(SubwayRepository subwayRepository) {
        this.subwayRepository = subwayRepository;
    }

    /**
     * /subways 获取所有的地铁线路
     *
     * @return 地铁线路分页
     */
    @GetMapping
    public ApiResponse getAllSubways(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size) {
        return ApiResponse.success(subwayRepository.findAll(PageRequest.of(page, size)));
    }

    /**
     * 根据城市获取地铁线路
     *
     * @return 所有的地铁线路
     */
    @GetMapping(params = "cityEnName")
    public ApiResponse getSubwaysByCityEnName(@RequestParam("cityEnName") String cityEnName) {
        return ApiResponse.success(subwayRepository.findByCityEnName(cityEnName));
    }

    /**
     * 根据城市获取地铁线路
     *
     * @return 所有的地铁线路
     */
    @GetMapping(params = {"cityEnName", "name"})
    public ApiResponse getSubwaysByCityEnName(@RequestParam("cityEnName") String cityEnName, @RequestParam("name") String name) {
        return ApiResponse.success(subwayRepository.findByCityEnNameAndName(cityEnName, name));
    }
}
