package view;

import controller.UserRegistrationController;
import model.User;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class UserRegistrationView extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public UserRegistrationView() {
        // Basic window setup
        setTitle("Cadastro - Airline Management Systemadmin@admin.com");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        // Add components
        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);

        // Center the window
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(800, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Cadastro de Usuário");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);

        // Registration form panel
        JPanel registrationPanel = new JPanel();
        registrationPanel.setLayout(new BoxLayout(registrationPanel, BoxLayout.Y_AXIS));
        registrationPanel.setBackground(Color.WHITE);
        registrationPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(200, 200, 200), 1),
            new EmptyBorder(30, 40, 30, 40)
        ));

        // Create form fields
        nameField = createStyledTextField("Nome completo");
        emailField = createStyledTextField("Email");
        passwordField = createStyledPasswordField("Senha");
        confirmPasswordField = createStyledPasswordField("Confirmar senha");

        // Create buttons
        JButton registerButton = createStyledButton("Criar Conta");
        JButton loginButton = createStyledButton("Voltar para Login");
        
        // Style the buttons differently
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(primaryColor);
        loginButton.setBorder(new LineBorder(primaryColor, 1));

        // Add action listeners
        registerButton.addActionListener(e -> handleUserRegistration());
        loginButton.addActionListener(e -> {
            new LoginView().setVisible(true);
            dispose();
        });

        // Add components with spacing
        registrationPanel.add(createFormLabel("Criar sua conta"));
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        registrationPanel.add(nameField);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registrationPanel.add(emailField);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registrationPanel.add(passwordField);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        registrationPanel.add(confirmPasswordField);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        registrationPanel.add(registerButton);
        registrationPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        registrationPanel.add(loginButton);

        mainPanel.add(registrationPanel);
        return mainPanel;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
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
                if (button.getBackground().equals(primaryColor)) {
                    button.setBackground(primaryColor.darker());
                } else {
                    button.setBackground(new Color(240, 240, 240));
                }
            }

            public void mouseExited(MouseEvent e) {
                if (button.getForeground().equals(Color.WHITE)) {
                    button.setBackground(primaryColor);
                } else {
                    button.setBackground(Color.WHITE);
                }
            }
        });

        return button;
    }

    private void handleUserRegistration() {
        // Get field values
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validate fields
        if (name.equals("Nome completo") || email.equals("Email") || 
            password.equals("Senha") || confirmPassword.equals("Confirmar senha")) {
            showError("Por favor, preencha todos os campos");
            return;
        }

        // Validate password match
        if (!password.equals(confirmPassword)) {
            showError("As senhas não correspondem");
            return;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Por favor, insira um email válido");
            return;
        }

        // Create user and attempt registration
        User user = new User(name, email, password, "seller");
        UserRegistrationController controller = new UserRegistrationController();

        try {
            controller.registerUser(user);
            JOptionPane.showMessageDialog(this,
                "Cadastro realizado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            new LoginView().setVisible(true);
            this.dispose();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro ao registrar usuário: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this,
            message,
            "Erro",
            JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> new UserRegistrationView().setVisible(true));
    }
}