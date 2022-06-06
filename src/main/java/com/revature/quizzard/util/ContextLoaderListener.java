package com.revature.quizzard.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.servlets.AuthServlet;
import com.revature.quizzard.servlets.FlashcardServlet;
import com.revature.quizzard.servlets.UserServlet;

import javax.servlet.*;
import java.time.LocalDateTime;

public class ContextLoaderListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        System.out.println("[LOG] - The servlet context was initialized at " + LocalDateTime.now());

        ObjectMapper mapper = new ObjectMapper();

        FlashcardDAO cardDAO = new FlashcardDAO();
        FlashcardService cardService = new FlashcardService(cardDAO);
        FlashcardServlet flashcardServlet = new FlashcardServlet(mapper, cardService);

        UserServlet userServlet = new UserServlet(mapper);
        AuthServlet authServlet = new AuthServlet(mapper);

        ServletContext context = sce.getServletContext();

//        CustomFilter customFilter = new CustomFilter();
//        context.addFilter("CustomFilter", customFilter)
//               .addMappingForUrlPatterns(EnumSet.of(DispatcherType.), true, "/*");

        context.addServlet("FlashcardServlet", flashcardServlet).addMapping("/flashcards/*");
        context.addServlet("AuthServlet", authServlet).addMapping("/auth");

        ServletRegistration.Dynamic registeredServlet = context.addServlet("UserServlet", userServlet);
        registeredServlet.setLoadOnStartup(3);
        registeredServlet.setInitParameter("user-servlet-key", "user-servlet-value");
        registeredServlet.setInitParameter("another-param", "another-value");
        registeredServlet.addMapping("/users/*");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("[LOG] - The servlet context was destroyed at " + LocalDateTime.now());
    }

}
