package screens;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
public class AdminDashboard extends Frame {
    Button createQuizButton = new Button("Create Quiz");
    Button logoutButton = new Button("Logout");

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setLayout(new FlowLayout());
        add(createQuizButton);
        add(logoutButton); // 🔹 Add logout button

        createQuizButton.addActionListener(e -> new QuizCreationScreen());

        logoutButton.addActionListener(e -> { // 🔹 Handle logout
//            Session.destroySession();
            this.dispose();
            new LoginScreen();
        });

        setSize(300, 120);
        setVisible(true);
    }
}
