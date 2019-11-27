package com.ido.es.rest.client;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

/**
 * @author xu.qiang
 * @date 19/11/26
 */
public class AppSearchType {

    private RestHighLevelClient client;

    @Before
    public void before() {

        RestClientBuilder node1 = RestClient.builder(
                new HttpHost("192.168.1.121", 9200, "http"));
        client = new RestHighLevelClient(node1);

    }

    @Test
    public void testSearch() throws IOException {


        /**
         * 6.X只保留了
         * query then fetch 排名不一定准 查2次
         * dfs query then fetch 排名肯定准
         *
         */


        SearchRequest searchRequest = new SearchRequest("yuren_index");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("name", "lisi"));
        searchRequest.source(searchSourceBuilder);

        searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse search = client.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(search));

        SearchHits hits = search.getHits();
        System.out.println(JSON.toJSONString(hits));


    }


    @After
    public void after() throws IOException {
        client.close();
    }


}
