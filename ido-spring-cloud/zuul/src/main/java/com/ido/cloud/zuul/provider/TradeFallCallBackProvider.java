package com.ido.cloud.zuul.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@Component
public class TradeFallCallBackProvider implements FallbackProvider {
    @Override
    public String getRoute() {
        return "TRADE";
    }

    @Override
    public ClientHttpResponse fallbackResponse(String s, Throwable throwable) {


        throwable.printStackTrace();


        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return HttpStatus.OK.value();
            }

            @Override
            public String getStatusText() throws IOException {
                return HttpStatus.OK.getReasonPhrase();
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {

                Map<String,Object> map  = new HashMap<String,Object>();
                map.put("status",200);
                map.put("message","无法连接，请检查你的网络");
                return new ByteArrayInputStream(new ObjectMapper().writeValueAsString(map).getBytes("UTF-8"));
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                return headers;
            }
        };
    }
}
