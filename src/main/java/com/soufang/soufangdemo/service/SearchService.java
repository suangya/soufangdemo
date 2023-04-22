package com.soufang.soufangdemo.service;

import com.soufang.soufangdemo.dto.HouseDTO;
import com.soufang.soufangdemo.dto.HouseSearchParams;

import java.util.List;

public interface SearchService {
    List<String> getSuggestions(String prefix);

    List<HouseDTO> search(HouseSearchParams params);
}
