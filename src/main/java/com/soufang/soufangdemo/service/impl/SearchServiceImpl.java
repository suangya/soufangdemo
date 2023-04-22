package com.soufang.soufangdemo.service.impl;

import com.soufang.soufangdemo.dto.HouseDTO;
import com.soufang.soufangdemo.dto.HouseSearchParams;
import com.soufang.soufangdemo.entity.es.HouseDocument;
import com.soufang.soufangdemo.repository.HouseRepository;
import com.soufang.soufangdemo.service.SearchService;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.suggest.response.Suggest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private ElasticsearchRestTemplate template;
    private final HouseRepository houseRepository;

    public SearchServiceImpl(ElasticsearchRestTemplate template, HouseRepository houseRepository) {
        this.template = template;
        this.houseRepository = houseRepository;
    }

    @Override
    public List<String> getSuggestions(String prefix) {
        CompletionSuggestionBuilder suggestionBuilder = SuggestBuilders.completionSuggestion("suggest").prefix(prefix).size(5).skipDuplicates(true);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("autocomplete",suggestionBuilder);
        Query query = new NativeSearchQueryBuilder().withSuggestBuilder(suggestBuilder).build();
        SearchHits<HouseDocument> search = template.search(query, HouseDocument.class);
        Suggest suggest = search.getSuggest();
        return suggest.getSuggestion("autocomplete")
                .getEntries()
                .stream()
                .flatMap(e -> e.getOptions().stream())
                .map(o -> o.getText())
                .collect(Collectors.toList());
    }

    @Override
    public List<HouseDTO> search(HouseSearchParams params) {
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.filter(
                QueryBuilders.termQuery("cityEnName", params.getCityEnName())
        );
        builder.must(
                QueryBuilders.multiMatchQuery(
                        params.getKeyword(),
                        "title"
                )
        );
        builder.must(
                QueryBuilders.rangeQuery("price").from(params.getPriceMin()).to(params.getPriceMax())
        );
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(builder)
                .withPageable(PageRequest.of(params.getPage(), params.getSize()))
                .build();
        SearchHits<HouseDocument> search = template.search(query, HouseDocument.class);
        return houseRepository.findAllById(search.getSearchHits()
                .stream().map(h->h.getContent().getHouseId()).collect(Collectors.toList()))
                .stream().map(h -> {
                    HouseDTO dto = new HouseDTO();
                    BeanUtils.copyProperties(h,dto);
                    return dto;
                }).collect(Collectors.toList());
    }
}
