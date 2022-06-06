package com.revature.quizzard.services;

import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.dto.ResourceCreationResponse;
import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.util.exceptions.InvalidRequestException;

public class FlashcardService {

    private final FlashcardDAO cardDAO;

    public FlashcardService(FlashcardDAO cardDAO) {
        this.cardDAO = cardDAO;
    }

    public ResourceCreationResponse createNewCard(Flashcard newCard) {

        // Validate the data provided from the web layer
        if (newCard == null ||
            newCard.getQuestionText() == null || newCard.getQuestionText().equals("") ||
            newCard.getAnswerText() == null || newCard.getAnswerText().equals(""))
        {
            String msg = "Provided card data was invalid. Question and answer text must not be null or empty!";
            // Logger.log(msg, LogLevel.ERROR);
            throw new InvalidRequestException(msg);
        }

        // If valid, persist to DB and return its result
        return new ResourceCreationResponse(cardDAO.save(newCard).getId());

    }
}
