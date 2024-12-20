package Manager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinanceManager {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
}

class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;

    public LoginFrame() {
        setTitle("Finance Manager - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel usernameLabel = new JLabel("Username:");
        styleLabel(usernameLabel);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(10);
        styleTextField(usernameField);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        styleLabel(passwordLabel);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(10);
        styleTextField(passwordField);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        add(loginButton, gbc);

        gbc.gridy = 3;
        registerButton = new JButton("Register");
        styleButton(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new RegisterFrame().setVisible(true);
                dispose();
            }
        });
        add(registerButton, gbc);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLACK);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}

class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;

    public RegisterFrame() {
        setTitle("Finance Manager - Register");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel usernameLabel = new JLabel("Username:");
        styleLabel(usernameLabel);
        add(usernameLabel, gbc);

        gbc.gridx = 1;
        usernameField = new JTextField(10);
        styleTextField(usernameField);
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel passwordLabel = new JLabel("Password:");
        styleLabel(passwordLabel);
        add(passwordLabel, gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(10);
        styleTextField(passwordField);
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        registerButton = new JButton("Register");
        styleButton(registerButton);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });
        add(registerButton, gbc);

        gbc.gridy = 3;
        backButton = new JButton("Back to Login");
        styleButton(backButton);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoginFrame().setVisible(true);
                dispose();
            }
        });
        add(backButton, gbc);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setBackground(Color.LIGHT_GRAY);
        textField.setForeground(Color.BLACK);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void styleLabel(JLabel label) {
        label.setFont(new Font("Arial", Font.BOLD, 16));
        label.setForeground(Color.BLACK);
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            new LoginFrame().setVisible(true);
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
