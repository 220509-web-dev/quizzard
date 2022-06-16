package com.revature.quizzard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.AppUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        List<AppUser> users = new ArrayList<>();
        users.addAll(Arrays.asList(
                new AppUser(1, "Adam", "Inn", "adam.inn@revature.com", "admin", "revature"),
                new AppUser(2, "Tester", "McTesterson", "tester@revature.com", "tester", "password"),
                new AppUser(3, "Alice", "Anderson", "alice.anderson@revature.com", "aanderson", "p4ssw0rd"),
                new AppUser(4, "Bob", "Bailey", "bob.bailey@revature.com", "bbailey", "p4$$W0rD"),
                new AppUser(5, "Charles", "Cantrell", "charles.cantrell@revature.com", "ccantrell", "PASSWORD")
        ));

        resp.getWriter().write(mapper.writeValueAsString(users));

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
