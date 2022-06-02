package com.revature.quizzard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.models.Flashcard;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet(
//    urlPatterns = "/flashcards",
//    loadOnStartup = 2,
//    initParams = {
//            @WebInitParam(name = "flashcard-servlet-key", value = "flashcard-servlet-value"),
//            @WebInitParam(name = "another-param", value = "another-value")
//    }
//) // annotation-based servlet registration
public class FlashcardServlet extends HttpServlet {

    // This is bad practice, since we would need to create a new ObjectMapper for every servlet
    // instantiated in this manner
    private final ObjectMapper mapper;
    private final FlashcardDAO cardDAO;

    public FlashcardServlet(ObjectMapper mapper, FlashcardDAO cardDAO) {
        this.mapper = mapper;
        this.cardDAO = cardDAO;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Flashcard> cards = cardDAO.getCards();
        String respPayload = mapper.writeValueAsString(cards);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);

    }

}
