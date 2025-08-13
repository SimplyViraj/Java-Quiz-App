package screens;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import src.DBConnection;

import java.sql.*;

public class SignupScreen extends Frame {
    TextField usernameField = new TextField();
    TextField passwordField = new TextField();
    Button signupButton = new Button("Sign Up");

    public SignupScreen() {
        setTitle("User Signup");
        setLayout(new GridLayout(3, 2));
        add(new Label("Username:")); add(usernameField);
        add(new Label("Password:")); add(passwordField);
        add(signupButton);

        passwordField.setEchoChar('*');

        signupButton.addActionListener(e -> handleSignup());

        setSize(300, 150);
        setVisible(true);
    }

    void handleSignup() {
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (username, password, role) VALUES (?, ?, 'user')");
            stmt.setString(1, usernameField.getText());
            stmt.setString(2, passwordField.getText());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Signup successful!");
            this.dispose();
            new LoginScreen();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error during signup.");
        }
    }
}