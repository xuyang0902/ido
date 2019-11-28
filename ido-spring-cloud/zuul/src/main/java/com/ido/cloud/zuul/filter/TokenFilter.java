package com.ido.cloud.zuul.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xu.qiang
 * @date 19/11/28
 */
@Component
public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {


//        return FilterConstants.PRE_TYPE;//路由前
//        return FilterConstants.POST_TYPE;//路由后
//        return FilterConstants.ERROR_TYPE;//发生错误时
        return FilterConstants.ROUTE_TYPE;//路由时
    }

    @Override
    public int filterOrder() {
        return 0;//越小约先
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取http请求
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();

        String token = request.getParameter("token");
        if(token == null){
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            try {
                HttpServletResponse response = currentContext.getResponse();
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                Map<String,Object> map  = new HashMap<String,Object>();
                map.put("status",200);
                map.put("message","非法请求");

                response.getWriter().write(new ObjectMapper().writeValueAsString(map));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
