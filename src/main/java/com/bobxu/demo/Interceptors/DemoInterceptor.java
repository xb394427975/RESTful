package com.bobxu.demo.Interceptors;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DemoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String theMethod = request.getMethod();

        if (HttpMethod.GET.matches(theMethod) || HttpMethod.POST.matches(theMethod) || HttpMethod.PUT.matches(theMethod)) {
            // GET, POST methods are allowed
            return true;
        }
        else {
            // everything else is not allowed
            response.sendError(HttpStatus.METHOD_NOT_ALLOWED.value());
            return false;
        }
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {

    }
}