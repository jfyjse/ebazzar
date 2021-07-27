package com.sayone.ebazzar.service;

import com.sayone.ebazzar.document.ElasticProduct;
import com.sayone.ebazzar.repository.ProductElasticRepository;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;


@Service
public class SearchServices {

    @Autowired
    ProductElasticRepository productElasticRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchTemplate;

    public List<ElasticProduct> getProductByName(String name) {
        String data=String.format(".*%s.*",name);
        NativeSearchQuery searchQuery1 = new NativeSearchQueryBuilder()
                .withFilter(regexpQuery("name", data))
                .build();

        SearchHits<ElasticProduct> products = elasticsearchTemplate
                .search(searchQuery1,ElasticProduct.class,IndexCoordinates.of("product"));

        List<ElasticProduct> returnValue = new ArrayList<ElasticProduct>();

        products.getSearchHits().forEach(searchHit->{
            returnValue.add(searchHit.getContent());
        });

        return returnValue;
    }


    public List<ElasticProduct> getProdutBySubcategory(String name){
        String data=String.format(".*%s.*",name);
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("subcategory", data).operator(Operator.AND)
                .fuzziness(Fuzziness.AUTO)
                .prefixLength(3))
                .build();

        SearchHits<ElasticProduct> products = elasticsearchTemplate
                .search(searchQuery,ElasticProduct.class,IndexCoordinates.of("product"));

        List<ElasticProduct> returnValue = new ArrayList<ElasticProduct>();

        products.getSearchHits().forEach(searchHit->{
            returnValue.add(searchHit.getContent());
        });

        return returnValue;
    }

}
