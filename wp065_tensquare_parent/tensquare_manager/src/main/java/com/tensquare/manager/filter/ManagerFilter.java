package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpServletRequest request;
    /**
     * 在请求前执行 pre 或者 后post执行
     * @return
     */

    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多个过滤器的执行顺序,数字越小,表示越先执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    // 是否执行该过滤器，此处为true，说明需要过滤
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤器内执行的操作 return 任何object的值都表示继续执行
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器");

        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();

        if(request.getMethod().equals("OPTIONS")){
            return null;
        }

        String url=request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登陆页面"+url);
            return null;
        }

        String header = request.getHeader("Authorization");
        if (header!=null && !"".equals(header)){
            if (header.startsWith("Bearer ")){
                String token = header.substring(7);
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String  roles = (String) claims.get("roles");
                    if(roles.equals("admin")){
                        //把头信息转发下去并且放行
                        requestContext.addZuulRequestHeader("Authorization",header);
                        return null;
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    requestContext.setSendZuulResponse(false); //终止运行
                }
            }
        }
        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(403);
        requestContext.setResponseBody("权限不足");
        requestContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }


}
