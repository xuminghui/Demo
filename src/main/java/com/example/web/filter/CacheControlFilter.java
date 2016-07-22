package com.example.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpHeader;
@WebFilter(filterName="cacheControlFilter",urlPatterns={"/img/*","/css/*"})
public class CacheControlFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//System.out.println("startCacheControlFilter");
		chain.doFilter(request, response);
		//HttpServletResponse httpResponse=(HttpServletResponse)response;
		//System.out.println(HttpHeader.CACHE_CONTROL.toString());
		//httpResponse.setHeader(HttpHeader.CACHE_CONTROL.toString(),"max-age=1000000");
		//System.out.println("endCacheControlFilter");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
