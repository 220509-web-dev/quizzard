package com.revature.quizzard.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

//@WebFilter("/*")
public class CorsFilter extends HttpFilter {

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - CustomFilter initialized at " + LocalDateTime.now());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("[LOG] - CorsFilter intercepted web request at " + LocalDateTime.now());
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(req, resp);
    }

}
