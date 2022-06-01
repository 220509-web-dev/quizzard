package com.revature.quizzard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.Flashcard;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
    urlPatterns = "/flashcards",
    loadOnStartup = 2,
    initParams = {
            @WebInitParam(name = "flashcard-servlet-key", value = "flashcard-servlet-value"),
            @WebInitParam(name = "another-param", value = "another-value")
    }
) // annotation-based servlet registration
public class FlashcardServlet extends HttpServlet {

    // This is bad practice, since we would need to create a new ObjectMapper for every servlet
    // instantiated in this manner
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Pretend this value came from the database
        Flashcard card = new Flashcard(123, "What does OOP stand for?", "Object Oriented Programming");

        String respPayload = mapper.writeValueAsString(card);
        resp.setContentType("application/json");
        resp.getWriter().write(respPayload);

    }

}
