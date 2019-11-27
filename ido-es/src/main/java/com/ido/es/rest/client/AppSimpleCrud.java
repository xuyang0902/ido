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
public class AppSimpleCrud {

    private RestHighLevelClient client;

    @Before
    public void before() {

        RestClientBuilder node1 = RestClient.builder(
                new HttpHost("192.168.1.121", 9200, "http"));
        client = new RestHighLevelClient(node1);

    }

    @Test
    public void testIndex() throws IOException {
        //创建index type id
        IndexRequest indexRequest = new IndexRequest("tbj", "sb", "1");
        String jsonStr = "{\"name\":\"suolongsb\",\"age\":19}";
        indexRequest.source(jsonStr, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(indexResponse));


        //绑定source的第二种方式
        IndexRequest indexRequest2 = new IndexRequest("tbj", "sb", "2");
        XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
        xContentBuilder.startObject();
        xContentBuilder.field("name", "sbbbbb");
        xContentBuilder.timeField("date", new Date());
        xContentBuilder.endObject();
        indexRequest2.source(xContentBuilder);
        IndexResponse indexResponse2 = client.index(indexRequest2);
        System.out.println(indexResponse2);


        //绑定source的第3种方式
        IndexRequest indexRequest3 = new IndexRequest("tbj", "sb", "3");
        indexRequest3.source("name", "hehe",
                "age", 10,
                "date", new Date());
        IndexResponse indexResponse3 = client.index(indexRequest3);
        System.out.println(indexResponse3);

    }


    @Test
    public void testExists() throws IOException {

        //判断
        GetRequest getRequest = new GetRequest("yuren_index", "user", "1");
        //判断不需要拿source
        getRequest.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        boolean exists = client.exists(getRequest, RequestOptions.DEFAULT);
        if (exists) {
            System.out.println("/yuren_index/user/1 存在");
        } else {
            System.out.println("/yuren_index/user/1 不存在");
        }

    }

    @Test
    public void testGet() throws IOException {

        //GET
        GetRequest getRequest = new GetRequest("yuren_index", "user", "1");
        GetResponse documentFields = client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(documentFields));

    }

    @Test
    public void update() throws InterruptedException {
        UpdateRequest updateRequest = new UpdateRequest("yuren_index","user","1");

        updateRequest.doc("{\"name\":\"fuck\",\"sex\":\"fuck2\"}",XContentType.JSON);
        client.updateAsync(updateRequest, RequestOptions.DEFAULT, new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {

                System.out.println(JSON.toJSONString(updateResponse));
            }

            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
            }
        });

        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void delete() throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("yuren_index","user","1");
        DeleteResponse delete = client.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println(delete);
    }


    @After
    public void after() throws IOException {
        client.close();
    }


}
