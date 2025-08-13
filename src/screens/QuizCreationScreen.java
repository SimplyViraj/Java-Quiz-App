package screens;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import src.DBConnection;

public class QuizCreationScreen extends Frame {
    TextField titleField = new TextField();
    TextField subjectField = new TextField();
    Button createButton = new Button("Create");
    Button backButton = new Button("Back to Dashboard");

    public QuizCreationScreen() {
        setTitle("Create Quiz");
        setLayout(new GridLayout(4, 2));
        add(new Label("Title:")); add(titleField);
        add(new Label("Subject:")); add(subjectField);
        add(createButton);add(backButton);

        createButton.addActionListener(e -> handleCreate());
        backButton.addActionListener(e -> {
            this.dispose();
            new AdminDashboard();
        });

        setSize(300, 150);
        setVisible(true);
    }

    void handleCreate() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO quizzes (title, subject) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, titleField.getText());
            stmt.setString(2, subjectField.getText());
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int quizId = rs.getInt(1);
                this.dispose();
                new QuestionAdder(quizId);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
