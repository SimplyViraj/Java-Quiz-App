package screens;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import src.DBConnection;

public class QuestionAdder extends Frame {
    int quizId;
    TextField questionField = new TextField();
    TextField optionA = new TextField();
    TextField optionB = new TextField();
    TextField optionC = new TextField();
    TextField optionD = new TextField();
    TextField correctOption = new TextField();
    Button addQuestionButton = new Button("Add Question");
    Button finishButton = new Button("Finish");

    public QuestionAdder(int quizId) {
        this.quizId = quizId;
        setTitle("Add Questions");
        setLayout(new GridLayout(8, 2));

        add(new Label("Question:")); add(questionField);
        add(new Label("Option A:")); add(optionA);
        add(new Label("Option B:")); add(optionB);
        add(new Label("Option C:")); add(optionC);
        add(new Label("Option D:")); add(optionD);
        add(new Label("Correct Option (A/B/C/D):")); add(correctOption);
        add(addQuestionButton); add(finishButton);

        addQuestionButton.addActionListener(e -> addQuestion());
        finishButton.addActionListener(e -> {
            this.dispose();
            new AdminDashboard();
        });

        setSize(400, 300);
        setVisible(true);
    }

    void addQuestion() {
        try {
            Connection conn = DBConnection.getConnection();
            String questionText = questionField.getText();

            PreparedStatement checkStmt = conn.prepareStatement(
                "SELECT * FROM questions WHERE quiz_id = ? AND question_text = ?"
            );
            checkStmt.setInt(1, quizId);
            checkStmt.setString(2, questionText);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "This question already exists in the quiz.");
                return;
            }

            PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO questions (quiz_id, question_text, option_a, option_b, option_c, option_d, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, quizId);
            stmt.setString(2, questionText);
            stmt.setString(3, optionA.getText());
            stmt.setString(4, optionB.getText());
            stmt.setString(5, optionC.getText());
            stmt.setString(6, optionD.getText());
            stmt.setString(7, correctOption.getText().toUpperCase());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Question added!");

            questionField.setText("");
            optionA.setText("");
            optionB.setText("");
            optionC.setText("");
            optionD.setText("");
            correctOption.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add question.");
        }
    }
}

