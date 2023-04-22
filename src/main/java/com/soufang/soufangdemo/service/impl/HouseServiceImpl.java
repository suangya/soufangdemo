package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.dto.HouseParams;
import com.soufang.soufangdemo.entity.House;
import com.soufang.soufangdemo.entity.es.HouseDocument;
import com.soufang.soufangdemo.repository.HouseRepository;
import com.soufang.soufangdemo.repository.es.HouseDocumentRepository;
import com.soufang.soufangdemo.service.HouseService;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class HouseServiceImpl implements HouseService {
    private HouseRepository houseRepository;
    private KafkaTemplate<String, String> template;

    public HouseServiceImpl(HouseRepository houseRepository,
                            KafkaTemplate<String, String> template) {
        this.houseRepository = houseRepository;
        this.template = template;
    }

    @Override
    public void addHouse(HouseParams params) {
        House house = new House();
        house.setId(System.currentTimeMillis());
        house.setTitle(params.getTitle());
        house.setCityEnName(params.getCityEnName());
        house = houseRepository.save(house);

        template.send("house_index", String.valueOf(house.getId()));

    }
}
