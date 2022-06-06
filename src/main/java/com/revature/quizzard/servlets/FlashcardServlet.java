package com.revature.quizzard.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.dto.ErrorResponse;
import com.revature.quizzard.dto.ResourceCreationResponse;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.util.exceptions.DataSourceException;
import com.revature.quizzard.util.exceptions.InvalidRequestException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
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

    private final ObjectMapper mapper;
    private final FlashcardService cardService;

    public FlashcardServlet(ObjectMapper mapper, FlashcardService cardService) {
        this.mapper = mapper;
        this.cardService = cardService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        List<Flashcard> cards = cardDAO.getCards();
//
//        String respPayload = mapper.writeValueAsString(cards);
//
//        resp.setContentType("application/json");
//
//        resp.getWriter().write(respPayload);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        HttpSession session = req.getSession(false);
//        if (session == null) {
//            HashMap<String, Object> errorMessage = new HashMap<>();
//            errorMessage.put("code", 401);
//            errorMessage.put("message", "No session found on request");
//            errorMessage.put("timestamp", LocalDateTime.now().toString());
//
//            resp.setStatus(401); // UNAUTHORIZED (user needs to login)
//            resp.setContentType("application/json");
//            resp.getWriter().write(mapper.writeValueAsString(errorMessage));
//            return;
//        }

        resp.setContentType("application/json");

        // get flashcard data from request and attempt to persist it to the database
        try {
            Flashcard newCard = mapper.readValue(req.getInputStream(), Flashcard.class);
            ResourceCreationResponse payload = cardService.createNewCard(newCard);
            resp.setStatus(201); // CREATED
            resp.getWriter().write(mapper.writeValueAsString(payload));
        } catch (InvalidRequestException e) {
            resp.setStatus(400);
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(400, e.getMessage())));
        } catch (DataSourceException e) {
            resp.setStatus(500);
            System.out.println(e.getMessage()); // log this to a file ideally
            resp.getWriter().write(mapper.writeValueAsString(new ErrorResponse(500, "An internal error occurred. Devs please check application logs.")));
        }

    }
}
