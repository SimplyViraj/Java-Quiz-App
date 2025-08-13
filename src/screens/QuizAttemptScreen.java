package screens;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import model.Question;
import src.DBConnection;

import java.util.*;

public class QuizAttemptScreen extends Frame {
    int userId, quizId, score = 0, current = 0;
    java.util.List<Question> questions = new ArrayList<>();
    Label questionLabel = new Label();
    CheckboxGroup optionsGroup = new CheckboxGroup();
    Checkbox a, b, c, d;
    Button nextButton = new Button("Next");

    public QuizAttemptScreen(int userId, int quizId) {
        this.userId = userId;
        this.quizId = quizId;

        setTitle("Attempt Quiz");
        setLayout(new GridLayout(6, 1));

        a = new Checkbox("", optionsGroup, false);
        b = new Checkbox("", optionsGroup, false);
        c = new Checkbox("", optionsGroup, false);
        d = new Checkbox("", optionsGroup, false);

        add(questionLabel);
        add(a);
        add(b);
        add(c);
        add(d);
        add(nextButton);

        nextButton.addActionListener(e -> handleNext());

        loadQuestions();
        displayQuestion();

        setSize(400, 300);
        setVisible(true);
    }

    void loadQuestions() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM questions WHERE quiz_id = ?");
            stmt.setInt(1, quizId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(new Question(
                    rs.getInt("id"), quizId,
                    rs.getString("question_text"),
                    rs.getString("option_a"),
                    rs.getString("option_b"),
                    rs.getString("option_c"),
                    rs.getString("option_d"),
                    rs.getString("correct_option").charAt(0)));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void displayQuestion() {
        if (current < questions.size()) {
            Question q = questions.get(current);
            questionLabel.setText(q.questionText);
            a.setLabel("A. " + q.optionA);
            b.setLabel("B. " + q.optionB);
            c.setLabel("C. " + q.optionC);
            d.setLabel("D. " + q.optionD);
        } else {
            saveResult();
            JOptionPane.showMessageDialog(this, "Quiz Finished! Score: " + score);
            this.dispose();
        }
    }

    void handleNext() {
        if (current < questions.size()) {
            Question q = questions.get(current);
            Checkbox selected = optionsGroup.getSelectedCheckbox();
            if (selected != null && selected.getLabel().charAt(0) == q.correctOption) {
                score++;
            }
            current++;
            displayQuestion();
        }
    }

    void saveResult() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO results (user_id, quiz_id, score) VALUES (?, ?, ?)");
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            stmt.setInt(3, score);
            stmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
