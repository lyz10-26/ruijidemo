package com.example.ruijidemo.filter;

import com.alibaba.fastjson.JSON;
import com.example.ruijidemo.common.BaseContext;
import com.example.ruijidemo.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author lyz
 * @DATE 2023-07-03 16:42
 * 检查用户登录
 */

@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //获取本次请求url
        String requestURI = request.getRequestURI();

        log.info("拦截到请求: {}", request.getRequestURI());
        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };
        //判断是否需要处理
        boolean check = check(urls, requestURI);
        if (check) {
            log.info("不处理拦截请求: {}", request.getRequestURI());
            filterChain.doFilter(request, response);
            return;
        }
        //检查PC登录状态，如果已经登录则直接放行
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已经登录，id:{}",request.getSession().getAttribute("employee"));
           Long id= (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }
        //检查移动端登录状态，如果已经登录则直接放行
        if (request.getSession().getAttribute("user") != null) {
            log.info("用户已经登录，id:{}",request.getSession().getAttribute("user"));
            Long id= (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }
        //如果未登录则直接返回登录结果
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
//        filterChain.doFilter(request, response);
    }

    public boolean check(String[] urls, String requestUrl) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestUrl);
            if (match) {
                return true;
            }
        }
        return false;

    }
}
