package com.ido.springmvc.interceptors;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *


 （1）Access-Control-Allow-Origin
 该字段是必须的。它的值要么是请求时Origin字段的值，要么是一个*，表示接受任意域名的请求。
 （2）Access-Control-Allow-Credentials
 该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。设为true，即表示服务器明确许可，
 Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，如果服务器不要浏览器发送Cookie，删除该字段即可。


 */
public class WapInterceptor implements HandlerInterceptor {

    private static Pattern pattern = Pattern.compile("(http|https)://(\\w|\\.)*(tongbanjie|tbjfund|itongbanjie|tongzier|tbj)\\.(com|org)");

    public static void main(String[] args) {
        Matcher m = pattern.matcher("http://m.tongbanjie.com/tbj/page/register.html?redirectURL=http%3A%2F%2Fm.tongbanjie.com#page_1");
        Matcher f = pattern.matcher("http://mapi.tbjfund.com/tbj/page/register.html?redirectURL=http%3A%2F%2Fm.tongbanjie.com#page_1");
        Matcher or = pattern.matcher("http://mapi.itongbanjie.com/tbj/page/register.html?redirectURL=http%3A%2F%2Fm.tongbanjie.com#page_1");
        System.out.println(m.find());
        System.out.println(m.group());
        System.out.println(f.find());
        System.out.println(f.group());
        System.out.println(or.find());
        System.out.println(or.group());
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//		// 处理跨域请求.
        String referer = request.getHeader("Referer");
        if (referer != null) {
            Matcher m = pattern.matcher(referer);
            if (m.find()) {
                //允许请求来源
                response.setHeader("Access-Control-Allow-Origin", m.group());
                response.setHeader("Access-Control-Allow-Credentials", "true");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
