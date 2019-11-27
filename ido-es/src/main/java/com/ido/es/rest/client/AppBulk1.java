package com.ido.es.rest.client;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
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
public class AppBulk1 {

    private RestHighLevelClient client;

    @Before
    public void before() {

        RestClientBuilder node1 = RestClient.builder(
                new HttpHost("192.168.1.121", 9200, "http"));
        client = new RestHighLevelClient(node1);

    }

    @Test
    public void testIndex() throws IOException {

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(new IndexRequest("test_01","doc1","1").source(XContentType.JSON,"f1","fields1"));
        bulkRequest.add(new IndexRequest("test_02","doc2","1").source(XContentType.JSON,"f1","fields2"));
        bulkRequest.add(new IndexRequest("test_03","doc3","1").source(XContentType.JSON,"f1","fields3"));

        BulkResponse bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);

        System.out.println(JSON.toJSONString(bulk));

    }



    @Test
    public void mutiGet() throws InterruptedException, IOException {

        MultiGetRequest multiGetRequest = new MultiGetRequest();
        multiGetRequest.add(new MultiGetRequest.Item("test_01","doc1","1"));
        multiGetRequest.add(new MultiGetRequest.Item("test_02","doc2","1"));
        multiGetRequest.add(new MultiGetRequest.Item("test_03","doc3","1"));

        MultiGetResponse mget = client.mget(multiGetRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(mget));


        System.out.println("########");
        MultiGetItemResponse[] responses = mget.getResponses();

        for (MultiGetItemResponse respons : responses) {

            System.out.println(JSON.toJSONString(respons));
        }

    }



    @After
    public void after() throws IOException {
        client.close();
    }


}
