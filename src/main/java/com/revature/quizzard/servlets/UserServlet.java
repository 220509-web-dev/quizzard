package com.revature.quizzard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.AppUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;

public class UserServlet extends HttpServlet {

    private final ObjectMapper mapper;

    public UserServlet(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init() throws ServletException {
        System.out.println("[LOG] - UserServlet instantiated!");
        System.out.println("[LOG] - Init param, test-init-key: " + this.getServletConfig().getInitParameter("test-init-key"));
        System.out.println("[LOG] - Init param, user-servlet-key: " + this.getServletConfig().getInitParameter("user-servlet-key"));
        System.out.println("[LOG] - Init param, another-param: " + this.getServletConfig().getInitParameter("another-param"));
        System.out.println("[LOG] - Context param, test-context-key: " + this.getServletContext().getInitParameter("test-context-key"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - UserServlet received a GET request at " + LocalDateTime.now());

        // This value would actually come from some data source
        AppUser someUser = new AppUser(999, "Alice", "Anderson", "aanderson@revature.com", "aanderson83", "password");

        // We can also use HashMaps to construct JSON payloads
//        HashMap<String, Object> someUser = new HashMap<>();
//        someUser.put("id", 999);
//        someUser.put("firstName", "Alice");
//        someUser.put("lastName", "Anderson");

        System.out.println("[LOG] - Was request filtered? " + req.getAttribute("was-filtered"));

        String respPayload = mapper.writeValueAsString(someUser);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("[LOG] - UserServlet received a POST request at " + LocalDateTime.now());

        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            System.out.println(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.setStatus(204);

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doDelete(req, resp);
    }
}
