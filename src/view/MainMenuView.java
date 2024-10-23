package view;

import controller.CustomerController;
import controller.FlightController;
import controller.TicketController;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenuView extends JFrame {
    private CustomerController customerController;
    private FlightController flightController;
    private TicketController ticketController;

    // Cores e fontes consistentes com outras telas
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Color accentColor = new Color(41, 128, 185);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public MainMenuView() {
        initializeControllers();
        setupMainWindow();
        createAndShowGUI();
    }

    private void initializeControllers() {
        customerController = new CustomerController();
        flightController = new FlightController();
        ticketController = new TicketController();
    }

    private void setupMainWindow() {
        setTitle("Airline Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);
        setLocationRelativeTo(null);
    }

    private void createAndShowGUI() {
        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1000, 80));
        headerPanel.setLayout(new BorderLayout());

        // Título principal
        JLabel titleLabel = new JLabel("Painel de Controle");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(new EmptyBorder(0, 20, 0, 0));

        // Informações do usuário
        JPanel userInfo = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        userInfo.setBackground(primaryColor);
        JLabel userLabel = new JLabel("Bem-vindo, Usuário");
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(mainFont);
        userInfo.add(userLabel);
        userInfo.setBorder(new EmptyBorder(0, 0, 0, 20));

        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(userInfo, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);

        JPanel menuGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        menuGrid.setBackground(backgroundColor);
        menuGrid.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Criar cards de menu
        menuGrid.add(createMenuCard("Gerenciar Voos", "Cadastro e controle de voos",
                e -> new FlightView().setVisible(true)));

        menuGrid.add(createMenuCard("Emitir Passagens", "Venda e emissão de bilhetes",
                e -> new TicketView().setVisible(true)));

        menuGrid.add(createMenuCard("Gerenciar Clientes", "Cadastro e gestão de clientes",
                e -> new CustomerView(customerController).setVisible(true)));

        menuGrid.add(createMenuCard("Relatórios", "Visualização de relatórios e estatísticas",
                e -> showReportsDialog()));

        mainPanel.add(menuGrid);
        return mainPanel;
    }

    private JPanel createMenuCard(String title, String description, ActionListener action) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(20, 20, 20, 20)));

        // Título do card
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Descrição
        JLabel descLabel = new JLabel(description);
        descLabel.setFont(mainFont);
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botão
        JButton button = createStyledButton("Acessar");
        button.addActionListener(action);

        // Adiciona hover effect no card
        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                card.setBackground(new Color(250, 250, 250));
            }

            public void mouseExited(MouseEvent e) {
                card.setBackground(Color.WHITE);
            }
        });

        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(descLabel);
        card.add(Box.createRigidArea(new Dimension(0, 20)));
        card.add(button);

        return card;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));

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

    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton logoutButton = new JButton("Sair do Sistema");
        logoutButton.setFont(mainFont);
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(new Color(231, 76, 60));
        logoutButton.setBorder(new EmptyBorder(8, 15, 8, 15));
        logoutButton.setFocusPainted(false);

        logoutButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60).darker());
            }

            public void mouseExited(MouseEvent e) {
                logoutButton.setBackground(new Color(231, 76, 60));
            }
        });

        logoutButton.addActionListener(e -> {
            int response = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente sair do sistema?",
                    "Confirmar Saída",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            if (response == JOptionPane.YES_OPTION) {
                dispose();
                new LoginView().setVisible(true);
            }
        });

        footerPanel.add(logoutButton);
        return footerPanel;
    }

    private void showReportsDialog() {
        // Placeholder para funcionalidade futura de relatórios
        JOptionPane.showMessageDialog(
                this,
                "Funcionalidade de relatórios em desenvolvimento",
                "Em Breve",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new MainMenuView().setVisible(true));
    }
}