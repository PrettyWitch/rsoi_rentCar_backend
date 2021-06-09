package com.yuhan.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @author wlw
 * @date  2021/6/8
 */

@Component
@Slf4j
public class UrlFilter extends ZuulFilter {

    private    Map<String,Set<String>> urls= new LinkedHashMap<>();

    public UrlFilter()
    {
        super();
        //分配用户权限
        Set<String> users=new HashSet<>();
        users.add("/booking/booking/");
        users.add("/booking/booking/*");
        users.add("/booking/booking/*/finish");
        urls.put("user",users);
        //分配管理员权限
        Set<String> admins=new HashSet<>();
        admins.add("/car/car/create");
        admins.add("/car/car/delete/*");
        admins.add("/office/offices/create");
        admins.add("/office/offices/*/car/*");
        admins.add("/reports/**");
        urls.put("admin",admins);
    }


    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return  true;
    }

    @Override
    public Object run()  {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri=request.getRequestURI();
        log.info(uri);
        //uri匹配
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        String token= JwtUtils.getRequestToken(request);
        //是否为管理员
        log.info(token);
        //未登录 只能访问登录注册接口
        if(token == null  ){
            if(uri.contains("/session")){
                return null;
            }else{
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("please login");
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                return "please login";
            }
        }
        String role=JwtUtils.getRole(token.substring(7));
        log.info(token.substring(7));
        log.info(role);
        if("admin".equals(role)){
            log.info("转发");
            //转发token到服务
            log.info(token.substring(7));
            ctx.addZuulRequestHeader("header",token.substring(7));
            return  null;
        }
        //不是管理员 试图访问管理员的接口
        Set<String> set=urls.get("admin");
        for(String string: set)
        {
            if(antPathMatcher.match(string,uri)){
                ctx.setSendZuulResponse(false);
                ctx.setResponseBody("you do not have enough power");
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                return "you do not have enough power";
            }
        }
        //转发token到服务
        ctx.addZuulRequestHeader("token",token.substring(7));
        return  null;
    }
}
