package com.revature.quizzard.daos;

import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FlashcardDAO {

    public List<Flashcard> getCards() {

        List<Flashcard> cards = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM flashcards";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Flashcard card = new Flashcard();
                card.setId(rs.getInt("id"));
                card.setQuestionText(rs.getString("questiontext"));
                card.setAnswerText(rs.getString("answertext"));
                cards.add(card);
            }

        } catch (SQLException e) {
            System.err.println("An error occurred within FlashcardDAO#getCards");
        }

        return cards;

    }

}
