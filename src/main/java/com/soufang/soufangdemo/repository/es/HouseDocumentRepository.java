package com.soufang.soufangdemo.repository.es;

import com.soufang.soufangdemo.entity.es.HouseDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface HouseDocumentRepository extends ElasticsearchRepository<HouseDocument,String> {

}
