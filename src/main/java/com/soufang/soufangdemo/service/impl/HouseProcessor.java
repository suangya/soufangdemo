package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.entity.House;
import com.soufang.soufangdemo.entity.es.HouseDocument;
import com.soufang.soufangdemo.repository.HouseRepository;
import com.soufang.soufangdemo.repository.es.HouseDocumentRepository;
import org.springframework.data.elasticsearch.core.suggest.Completion;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.util.Optional;

@Service
public class HouseProcessor {
    private HouseDocumentRepository houseDocumentRepository;
    private HouseRepository houseRepository;

    public HouseProcessor(HouseDocumentRepository houseDocumentRepository, HouseRepository houseRepository) {
        this.houseDocumentRepository = houseDocumentRepository;
        this.houseRepository = houseRepository;
    }

    @KafkaListener(topics = "house_index")
    public void handleMessage(String id){
        Optional<House> opts = houseRepository.findById(Long.valueOf(id));
        if(!opts.isPresent()){
            return;
        }
        House house = opts.get();
        HouseDocument document = new HouseDocument();
        document.setHouseId(house.getId());
        document.setTitle(house.getTitle());
        document.setCityEnName(house.getCityEnName());
        Completion completion = new Completion();
        completion.setInput(new String[]{house.getTitle(), house.getCityEnName()});
        document.setSuggest(completion);
        houseDocumentRepository.save(document);
    }
}
