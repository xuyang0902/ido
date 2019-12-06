package com.ido.gateway.route;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class GatewayRouteDefinition {
    //路由的Id
    private String id;
    //路由断言集合配置
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();
    //路由过滤器集合配置
    private List<GatewayFilterDefinition> filters = new ArrayList<>();
    //路由规则转发的目标uri
    private String uri;
    //路由执行的顺序
    private int order = 0;
    //此处省略get和set方法


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<GatewayPredicateDefinition> getPredicates() {
        return predicates;
    }

    public void setPredicates(List<GatewayPredicateDefinition> predicates) {
        this.predicates = predicates;
    }

    public List<GatewayFilterDefinition> getFilters() {
        return filters;
    }

    public void setFilters(List<GatewayFilterDefinition> filters) {
        this.filters = filters;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }


    public static void main(String[] args) {


        GatewayRouteDefinition gatewayRouteDefinition = new GatewayRouteDefinition();


        gatewayRouteDefinition.setId("trade");
        gatewayRouteDefinition.setUri("lb://TRADE");

        GatewayPredicateDefinition predicateDefinition = new GatewayPredicateDefinition();
        predicateDefinition.setName("Path");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("pattern","/trade/**");
        predicateDefinition.setArgs(linkedHashMap);
        gatewayRouteDefinition.setPredicates(Collections.singletonList(predicateDefinition));


        GatewayFilterDefinition filterDefinition = new GatewayFilterDefinition();

        filterDefinition.setName("StripPrefix");

        LinkedHashMap filterMap = new LinkedHashMap();
        filterMap.put("_genkey_0","1");
        filterDefinition.setArgs(filterMap);
        gatewayRouteDefinition.setFilters(Collections.singletonList(filterDefinition));


        System.out.println(JSON.toJSONString(gatewayRouteDefinition));

        /**
         *

         {"filters":[{"args":{"_genkey_0":"1"},"name":"StripPrefix"}],"id":"trade","order":0,"predicates":[{"args":{"pattern":"/trade/**"},"name":"Path"}],"uri":"lb://TRADE"}


         */

    }
}