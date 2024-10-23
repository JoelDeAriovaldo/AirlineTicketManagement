package view;

import controller.LoginController;
import model.User;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public LoginView() {
        // Basic window setup
        setTitle("Airline Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        // Main panel with shadow effect
        JPanel mainPanel = createMainPanel();
        add(createHeader(), BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);

        // Center the window
        setLocationRelativeTo(null);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Airline Management System");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);

        // Login form panel
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(20, 40, 20, 40)
        ));

        // Add components to login panel
        JLabel loginTitle = new JLabel("Login");
        loginTitle.setFont(titleFont);
        loginTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailField = createStyledTextField("Email");
        passwordField = createStyledPasswordField("Senha");
        JButton loginButton = createStyledButton("Entrar");
        JButton registerButton = createStyledButton("Criar Nova Conta");

        // Add action listeners
        loginButton.addActionListener(e -> handleLogin());
        registerButton.addActionListener(e -> {
            new UserRegistrationView().setVisible(true);
            dispose();
        });

        // Add components with spacing
        loginPanel.add(loginTitle);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(emailField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        loginPanel.add(loginButton);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        loginPanel.add(registerButton);

        mainPanel.add(loginPanel);
        return mainPanel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(mainFont);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));

        // Add placeholder
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField(20);
        field.setFont(mainFont);
        field.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(8, 10, 8, 10)
        ));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));
        
        // Add placeholder
        field.setEchoChar((char) 0);
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setEchoChar('●');
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(field.getPassword()).isEmpty()) {
                    field.setEchoChar((char) 0);
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 40));

        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(primaryColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });

        return button;
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        
        // Skip validation if fields contain placeholders
        if (email.equals("Email") || password.equals("Senha")) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha todos os campos",
                "Erro de Login",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        LoginController loginController = new LoginController();
        try {
            User user = loginController.login(email, password);
            if (user != null) {
                JOptionPane.showMessageDialog(this, 
                    "Login realizado com sucesso!",
                    "Bem-vindo",
                    JOptionPane.INFORMATION_MESSAGE);
                new MainMenuView().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Credenciais inválidas. Tente novamente.",
                    "Erro de Login",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Erro ao realizar login: " + e.getMessage(),
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new LoginView().setVisible(true));
    }
}