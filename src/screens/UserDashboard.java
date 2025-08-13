package screens;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;

import src.DBConnection;

public class UserDashboard extends Frame {
    int userId;
    Choice quizList = new Choice();
    Button attemptButton = new Button("Attempt Quiz");
    Button logoutButton = new Button("Logout");

    public UserDashboard(int userId) {
        this.userId = userId;
        setTitle("User Dashboard");
        setLayout(new FlowLayout());
        
        add(new Label("Select Quiz:"));
        add(quizList);
        add(attemptButton);
        add(logoutButton); // 🔹 Add logout button to GUI

        loadQuizzes();

        attemptButton.addActionListener(e -> {
            String selected = quizList.getSelectedItem();
            int quizId = Integer.parseInt(selected.split(" - ")[0]);
            new QuizAttemptScreen(userId, quizId);
        });

        logoutButton.addActionListener(e -> { // 🔹 Handle logout
//            Session.destroySession();
            this.dispose();
            new LoginScreen();
        });

        setSize(300, 180);
        setVisible(true);
    }

    void loadQuizzes() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM quizzes");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                quizList.add(rs.getInt("id") + " - " + rs.getString("title"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
