package com.lee.mydemo.Interceptor;

import com.lee.mydemo.annotation.SkipToken;
import com.lee.mydemo.config.UserConfig;
import com.lee.mydemo.utils.JwtUtils;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class Interceptor implements HandlerInterceptor {
    @Autowired
    private UserConfig config;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// HttpServletRequest 请求头中取出 token
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        //检查是否有passtoken注释，有则跳过认证
//        if (method.isAnnotationPresent(SkipToken.class)) {
//            SkipToken passToken = method.getAnnotation(SkipToken.class);
//            if (passToken.required()) {
//                return true;
//            }
//        }
        String id = JwtUtils.decode(token);
        if (id.equals(config.getId()))
            return true;
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
