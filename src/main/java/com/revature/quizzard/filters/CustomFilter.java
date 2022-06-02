package com.revature.quizzard.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

//@WebFilter("/*")
public class CustomFilter extends HttpFilter {

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - CustomFilter initialized at " + LocalDateTime.now());
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("[LOG] - CustomFilter intercepted web request at " + LocalDateTime.now());
        req.setAttribute("was-filtered", true);
        resp.setHeader("example-response-header", "some-example-value");
        chain.doFilter(req, resp);
    }

}
