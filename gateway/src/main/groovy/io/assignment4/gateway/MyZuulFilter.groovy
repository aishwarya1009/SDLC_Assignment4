package io.assignment4.gateway


import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class MyZuulFilter extends ZuulFilter {
    @Override
    String filterType() {
        return "pre";
    }

    @Override
    int filterOrder() {
        return 1;
    }

    @Override
    boolean shouldFilter() {
        return true;
    }

    @Override
    Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        return null;
    }
}