package com.revature.quizzard.daos;

import com.revature.quizzard.models.Flashcard;
import com.revature.quizzard.util.ConnectionFactory;
import com.revature.quizzard.util.exceptions.DataSourceException;

import java.sql.*;
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

    public Flashcard save(Flashcard newCard) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "INSERT INTO flashcards VALUES (default, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, newCard.getQuestionText());
            pstmt.setString(2, newCard.getAnswerText());

            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted != 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                rs.next();
                newCard.setId(rs.getInt("id"));
                return newCard;
            }

            // TODO clean up later
            throw new RuntimeException("Should never be here");


        } catch (SQLException e) {
            throw new DataSourceException("An error occurred during data access", e);
        }

    }

}
