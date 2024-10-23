package view;

/**
 *
 * @author JoelDeAriovaldo
 */

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;

import controller.CustomerController;
import controller.FlightController;
import controller.TicketController;
import model.Customer;
import model.Flight;
import model.Ticket;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

public class TicketView extends JFrame {
    private JComboBox<Flight> flightComboBox;
    private JComboBox<Customer> customerComboBox;
    private JTextField seatNumberField, baggageField, totalPriceField;
    private JTable ticketTable;
    private DefaultTableModel tableModel;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public TicketView() {
        setTitle("Emitir Passagem - Airline Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(backgroundColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(createHeader(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        add(createMainPanel(), gbc);

        gbc.gridy = 2;
        gbc.weighty = 0;
        add(createFooter(), gbc);

        setLocationRelativeTo(null);
        setResizable(true);

        loadFlightsAndCustomers();
        loadTicketData();
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Emitir Passagem");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(createFormPanel(), gbc);

        gbc.gridy = 1;
        gbc.weighty = 1;
        mainPanel.add(createTableScrollPane(), gbc);

        return mainPanel;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(20, 20, 20, 20)));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        flightComboBox = createStyledComboBox();
        customerComboBox = createStyledComboBox();
        seatNumberField = createStyledTextField("Número do Assento");
        baggageField = createStyledTextField("Bagagens");
        totalPriceField = createStyledTextField("Preço Total");

        JButton issueTicketButton = createStyledButton("Emitir Passagem");
        issueTicketButton.addActionListener(e -> handleIssueTicket());

        addFormField(formPanel, "Voo:", flightComboBox, gbc, 0);
        addFormField(formPanel, "Cliente:", customerComboBox, gbc, 1);
        addFormField(formPanel, "Número do Assento:", seatNumberField, gbc, 2);
        addFormField(formPanel, "Bagagens:", baggageField, gbc, 3);
        addFormField(formPanel, "Preço Total:", totalPriceField, gbc, 4);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        formPanel.add(issueTicketButton, gbc);

        return formPanel;
    }

    private JScrollPane createTableScrollPane() {
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Voo", "Cliente", "Assento", "Bagagens", "Preço Total" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        ticketTable = new JTable(tableModel);
        ticketTable.setRowHeight(35);
        ticketTable.setShowVerticalLines(false);
        ticketTable.setSelectionBackground(primaryColor);
        ticketTable.setSelectionForeground(Color.WHITE);
        ticketTable.getTableHeader().setBackground(backgroundColor);
        ticketTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        return scrollPane;
    }

    private JPanel createFooter() {
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JButton backButton = new JButton("Voltar ao Menu Principal");
        backButton.setFont(mainFont);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(primaryColor);
        backButton.setBorder(new EmptyBorder(8, 15, 8, 15));
        backButton.setFocusPainted(false);

        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                backButton.setBackground(primaryColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                backButton.setBackground(primaryColor);
            }
        });

        backButton.addActionListener(e -> {
            new MainMenuView().setVisible(true);
            dispose();
        });

        footerPanel.add(backButton);
        return footerPanel;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(mainFont);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)));
        field.setPreferredSize(new Dimension(300, 40));
        field.setMaximumSize(new Dimension(300, 40));

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

    private JComboBox createStyledComboBox() {
        JComboBox comboBox = new JComboBox();
        comboBox.setFont(mainFont);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)));
        comboBox.setPreferredSize(new Dimension(300, 40));
        comboBox.setMaximumSize(new Dimension(300, 40));
        return comboBox;
    }

    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
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

    private void addFormField(JPanel panel, String label, JComponent field, GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Segoe UI", Font.BOLD, 12));
        panel.add(lblField, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        panel.add(field, gbc);
        gbc.weightx = 0.0;
    }

    private void loadFlightsAndCustomers() {
        FlightController flightController = new FlightController();
        CustomerController customerController = new CustomerController();

        try {
            List<Flight> flights = flightController.getAllFlights();
            for (Flight flight : flights) {
                flightComboBox.addItem(flight);
            }

            List<Customer> customers = customerController.getAllCustomers();
            for (Customer customer : customers) {
                customerComboBox.addItem(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTicketData() {
        try {
            TicketController ticketController = new TicketController();
            List<Ticket> tickets = ticketController.getAllTickets();
            tableModel.setRowCount(0); // Clear existing data
            for (Ticket ticket : tickets) {
                tableModel.addRow(new Object[] {
                        ticket.getId(),
                        ticket.getFlight().getFlightNumber(),
                        ticket.getCustomer().getName(),
                        ticket.getSeatNumber(),
                        ticket.getBaggage(),
                        ticket.getTotalPrice()
                });
            }
        } catch (SQLException e) {
            showError("Erro ao carregar dados das passagens: " + e.getMessage());
        }
    }

    private void handleIssueTicket() {
        Flight flight = (Flight) flightComboBox.getSelectedItem();
        Customer customer = (Customer) customerComboBox.getSelectedItem();
        String seatNumber = seatNumberField.getText();
        int baggage = Integer.parseInt(baggageField.getText());
        double totalPrice = Double.parseDouble(totalPriceField.getText());

        Ticket ticket = new Ticket(0, flight, customer, seatNumber, baggage, totalPrice, null); // Bilhete PDF pode ser
                                                                                                // adicionado depois
        TicketController controller = new TicketController();

        try {
            controller.addTicket(ticket);
            JOptionPane.showMessageDialog(this, "Passagem emitida com sucesso!");
            loadTicketData();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao emitir passagem: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        flightComboBox.setSelectedIndex(-1);
        customerComboBox.setSelectedIndex(-1);
        seatNumberField.setText("");
        baggageField.setText("");
        totalPriceField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
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

        SwingUtilities.invokeLater(() -> new TicketView().setVisible(true));
    }
}