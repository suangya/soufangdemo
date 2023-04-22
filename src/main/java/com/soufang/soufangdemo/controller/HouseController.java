package com.soufang.soufangdemo.controller;

import com.soufang.soufangdemo.base.ApiResponse;
import com.soufang.soufangdemo.dto.HouseParams;
import com.soufang.soufangdemo.dto.HouseSearchParams;
import com.soufang.soufangdemo.service.HouseService;
import com.soufang.soufangdemo.service.SearchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/houses")
public class HouseController {
    private HouseService houseService;
    private SearchService searchService;

    public HouseController(HouseService houseService, SearchService searchService) {
        this.houseService = houseService;
        this.searchService = searchService;
    }

    @GetMapping("/suggestions")
    public ApiResponse getSuggestions(String prefix){
        List<String> suggestions = searchService.getSuggestions(prefix);
        return ApiResponse.success(suggestions);
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody HouseSearchParams params){

        return ApiResponse.success(searchService.search(params));
    }

    @PostMapping
    public ApiResponse addHouse(@RequestBody HouseParams houseParams){
        houseService.addHouse(houseParams);
        return ApiResponse.success();
    }
}
