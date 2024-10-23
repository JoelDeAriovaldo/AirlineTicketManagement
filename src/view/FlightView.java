package view;

/**
 *
 * @author JoelDeAriovaldo
 */

import controller.FlightController;
import model.Flight;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class FlightView extends JFrame {
    private JTextField airlineField, originField, destinationField, flightNumberField, aircraftModelField,
            ticketPriceField;
    private JSpinner departureTimeSpinner, arrivalTimeSpinner, availableSeatsSpinner;
    private JTable flightTable;
    private DefaultTableModel tableModel;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public FlightView() {
        setTitle("Cadastrar Voo - Airline Management");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setResizable(false);

        loadFlightData();
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Cadastrar Voo");
        titleLabel.setFont(titleFont);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(backgroundColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = createFormPanel();
        JScrollPane tableScrollPane = createTableScrollPane();

        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

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

        airlineField = createStyledTextField("Companhia Aérea");
        originField = createStyledTextField("Origem");
        destinationField = createStyledTextField("Destino");
        flightNumberField = createStyledTextField("Número do Voo");
        aircraftModelField = createStyledTextField("Modelo da Aeronave");
        ticketPriceField = createStyledTextField("Preço do Bilhete");

        departureTimeSpinner = createStyledDateTimeSpinner();
        arrivalTimeSpinner = createStyledDateTimeSpinner();
        availableSeatsSpinner = new JSpinner(new SpinnerNumberModel(100, 1, 500, 1));

        JButton addButton = createStyledButton("Adicionar Voo");
        addButton.addActionListener(e -> handleAddFlight());

        addFormField(formPanel, "Companhia Aérea:", airlineField, gbc, 0);
        addFormField(formPanel, "Origem:", originField, gbc, 1);
        addFormField(formPanel, "Destino:", destinationField, gbc, 2);
        addFormField(formPanel, "Número do Voo:", flightNumberField, gbc, 3);
        addFormField(formPanel, "Modelo da Aeronave:", aircraftModelField, gbc, 4);
        addFormField(formPanel, "Horário de Partida:", departureTimeSpinner, gbc, 5);
        addFormField(formPanel, "Horário de Chegada:", arrivalTimeSpinner, gbc, 6);
        addFormField(formPanel, "Assentos Disponíveis:", availableSeatsSpinner, gbc, 7);
        addFormField(formPanel, "Preço do Bilhete:", ticketPriceField, gbc, 8);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        formPanel.add(addButton, gbc);

        return formPanel;
    }

    private JScrollPane createTableScrollPane() {
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Companhia Aérea", "Origem", "Destino", "Partida", "Chegada", "Número do Voo",
                        "Modelo da Aeronave", "Assentos Disponíveis", "Preço" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        flightTable = new JTable(tableModel);
        flightTable.setRowHeight(35);
        flightTable.setShowVerticalLines(false);
        flightTable.setSelectionBackground(primaryColor);
        flightTable.setSelectionForeground(Color.WHITE);
        flightTable.getTableHeader().setBackground(backgroundColor);
        flightTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        flightTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && flightTable.getSelectedRow() != -1) {
                loadFlightFromTable();
            }
        });

        JScrollPane scrollPane = new JScrollPane(flightTable);
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

    private JSpinner createStyledDateTimeSpinner() {
        JSpinner spinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, "dd/MM/yyyy HH:mm");
        spinner.setEditor(editor);
        spinner.setFont(mainFont);
        spinner.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)));
        spinner.setPreferredSize(new Dimension(300, 40));
        spinner.setMaximumSize(new Dimension(300, 40));
        return spinner;
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

    private void loadFlightData() {
        try {
            FlightController flightController = new FlightController();
            List<Flight> flights = flightController.getAllFlights();
            tableModel.setRowCount(0); // Clear existing data
            for (Flight flight : flights) {
                tableModel.addRow(new Object[] {
                        flight.getId(),
                        flight.getAirline(),
                        flight.getOrigin(),
                        flight.getDestination(),
                        flight.getDepartureTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        flight.getArrivalTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                        flight.getFlightNumber(),
                        flight.getAircraftModel(),
                        flight.getAvailableSeats(),
                        flight.getTicketPrice()
                });
            }
        } catch (SQLException e) {
            showError("Erro ao carregar dados dos voos: " + e.getMessage());
        }
    }

    private void loadFlightFromTable() {
        int selectedRow = flightTable.getSelectedRow();
        airlineField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        originField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        destinationField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        departureTimeSpinner
                .setValue(Date.from(LocalDateTime
                        .parse(tableModel.getValueAt(selectedRow, 4).toString(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        .atZone(ZoneId.systemDefault()).toInstant()));
        arrivalTimeSpinner
                .setValue(Date.from(LocalDateTime
                        .parse(tableModel.getValueAt(selectedRow, 5).toString(),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        .atZone(ZoneId.systemDefault()).toInstant()));
        flightNumberField.setText(tableModel.getValueAt(selectedRow, 6).toString());
        aircraftModelField.setText(tableModel.getValueAt(selectedRow, 7).toString());
        availableSeatsSpinner.setValue(Integer.parseInt(tableModel.getValueAt(selectedRow, 8).toString()));
        ticketPriceField.setText(tableModel.getValueAt(selectedRow, 9).toString());
    }

    private void handleAddFlight() {
        String airline = airlineField.getText();
        String origin = originField.getText();
        String destination = destinationField.getText();
        String flightNumber = flightNumberField.getText();
        String aircraftModel = aircraftModelField.getText();
        Date departureDate = (Date) departureTimeSpinner.getValue();
        Date arrivalDate = (Date) arrivalTimeSpinner.getValue();
        LocalDateTime departureTime = departureDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime arrivalTime = arrivalDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        int availableSeats = (int) availableSeatsSpinner.getValue();
        double ticketPrice = Double.parseDouble(ticketPriceField.getText());

        Flight flight = new Flight(airline, origin, destination, departureTime, arrivalTime, flightNumber,
                aircraftModel, availableSeats, ticketPrice);
        FlightController controller = new FlightController();

        try {
            controller.addFlight(flight);
            JOptionPane.showMessageDialog(this, "Voo adicionado com sucesso!");
            loadFlightData();
            clearForm();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao adicionar voo: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        airlineField.setText("");
        originField.setText("");
        destinationField.setText("");
        flightNumberField.setText("");
        aircraftModelField.setText("");
        departureTimeSpinner.setValue(new Date());
        arrivalTimeSpinner.setValue(new Date());
        availableSeatsSpinner.setValue(100);
        ticketPriceField.setText("");
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

        SwingUtilities.invokeLater(() -> new FlightView().setVisible(true));
    }
}