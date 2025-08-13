package screens;
import screens.SignupScreen;
import src.DBConnection;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;

import java.sql.*;

public class LoginScreen extends Frame {
    TextField usernameField = new TextField();
    TextField passwordField = new TextField();
    Button loginButton = new Button("Login");
    Button signupButton = new Button("Sign Up");

    public LoginScreen() {
        setTitle("Login");
        setLayout(new GridLayout(4, 2));
        add(new Label("Username:")); add(usernameField);
        add(new Label("Password:")); add(passwordField);
        add(loginButton); add(signupButton);

        passwordField.setEchoChar('*');

        loginButton.addActionListener(e -> handleLogin());
        signupButton.addActionListener(e -> {
            this.dispose();
            new SignupScreen();
        });

        setSize(300, 200);
        setVisible(true);
    }

    void handleLogin() {
        try 
        {
        	Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, passwordField.getText());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) 
            {
                String role = rs.getString("role");
                int userId = rs.getInt("id");
                this.dispose();
                if ("admin".equals(role)) new AdminDashboard();
                else new UserDashboard(userId);
            } 
            else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.");
            }
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
}
