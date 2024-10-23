package view;

/**
 *
 * @author JoelDeAriovaldo
 */

import model.Customer;
import controller.CustomerController;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class CustomerView extends JFrame {
    private CustomerController customerController;
    private JTable customerTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, cpfCnpjField, birthdateField, addressField, phoneField, emailField;
    private JButton addButton, editButton, deleteButton, clearButton;
    private Color primaryColor = new Color(70, 130, 180);
    private Color backgroundColor = new Color(245, 245, 245);
    private Font mainFont = new Font("Segoe UI", Font.PLAIN, 14);
    private Font titleFont = new Font("Segoe UI", Font.BOLD, 24);

    public CustomerView(CustomerController customerController) {
        this.customerController = customerController;
        initialize();

    }

    private void initialize() {
        setTitle("Gerenciamento de Clientes - Airline Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(backgroundColor);

        add(createHeader(), BorderLayout.NORTH);
        add(createMainPanel(), BorderLayout.CENTER);
        add(createFooter(), BorderLayout.SOUTH);

        loadCustomerData();
    }

    private JPanel createHeader() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(primaryColor);
        headerPanel.setPreferredSize(new Dimension(1000, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel titleLabel = new JLabel("Gerenciamento de Clientes");
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

        nameField = createStyledTextField("Nome completo");
        cpfCnpjField = createStyledTextField("BI");
        birthdateField = createStyledTextField("Data de Nascimento (YYYY-MM-DD)");
        addressField = createStyledTextField("Endereço");
        phoneField = createStyledTextField("Telefone");
        emailField = createStyledTextField("Email");

        addFormField(formPanel, "Nome:", nameField, gbc, 0);
        addFormField(formPanel, "BI:", cpfCnpjField, gbc, 1);
        addFormField(formPanel, "Data de Nascimento:", birthdateField, gbc, 2);
        addFormField(formPanel, "Endereço:", addressField, gbc, 3);
        addFormField(formPanel, "Telefone:", phoneField, gbc, 4);
        addFormField(formPanel, "Email:", emailField, gbc, 5);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButton = createStyledButton("Adicionar");
        editButton = createStyledButton("Editar");
        deleteButton = createStyledButton("Excluir");
        clearButton = createStyledButton("Limpar");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(clearButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        addButton.addActionListener(e -> addCustomer());
        editButton.addActionListener(e -> editCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());
        clearButton.addActionListener(e -> clearForm());

        return formPanel;
    }

    private JScrollPane createTableScrollPane() {
        tableModel = new DefaultTableModel(
                new Object[] { "ID", "Nome", "BI", "Nascimento", "Endereço", "Telefone", "Email" }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        customerTable = new JTable(tableModel);
        customerTable.setRowHeight(35);
        customerTable.setShowVerticalLines(false);
        customerTable.setSelectionBackground(primaryColor);
        customerTable.setSelectionForeground(Color.WHITE);
        customerTable.getTableHeader().setBackground(backgroundColor);
        customerTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));

        customerTable.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting() && customerTable.getSelectedRow() != -1) {
                loadCustomerFromTable();
            }
        });

        JScrollPane scrollPane = new JScrollPane(customerTable);
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

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(mainFont);
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setBorder(new EmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200, 40));

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

    private void addFormField(JPanel panel, String label, JTextField field, GridBagConstraints gbc, int row) {
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

    private void loadCustomerData() {
        try {
            List<Customer> customers = customerController.getAllCustomers();
            tableModel.setRowCount(0); // Clear existing data
            for (Customer customer : customers) {
                tableModel.addRow(new Object[] {
                        customer.getId(),
                        customer.getName(),
                        customer.getCpfCnpj(),
                        customer.getBirthdate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        customer.getAddress(),
                        customer.getPhone(),
                        customer.getEmail()
                });
            }
        } catch (SQLException e) {
            showError("Erro ao carregar dados dos clientes: " + e.getMessage());
        }
    }

    private void addCustomer() {
        String name = nameField.getText();
        String cpfCnpj = cpfCnpjField.getText();
        String birthdate = birthdateField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        try {
            LocalDate parsedBirthdate = parseDate(birthdate);
            customerController.addCustomer(new Customer(0, name, cpfCnpj, parsedBirthdate, address, phone, email));
            loadCustomerData();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato yyyy-MM-dd.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        String name = nameField.getText();
        String cpfCnpj = cpfCnpjField.getText();
        String birthdate = birthdateField.getText();
        String address = addressField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();

        try {
            LocalDate parsedBirthdate = parseDate(birthdate);
            customerController.updateCustomer(customerId, name, cpfCnpj, parsedBirthdate, address, phone, email);
            loadCustomerData();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato yyyy-MM-dd.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDate parseDate(String date) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    private void deleteCustomer() {
        int selectedRow = customerTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int customerId = (int) tableModel.getValueAt(selectedRow, 0);
        try {
            customerController.deleteCustomer(customerId);
            loadCustomerData();
            clearForm();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCustomerFromTable() {
        int selectedRow = customerTable.getSelectedRow();
        nameField.setText(tableModel.getValueAt(selectedRow, 1).toString());
        cpfCnpjField.setText(tableModel.getValueAt(selectedRow, 2).toString());
        birthdateField.setText(tableModel.getValueAt(selectedRow, 3).toString());
        addressField.setText(tableModel.getValueAt(selectedRow, 4).toString());
        phoneField.setText(tableModel.getValueAt(selectedRow, 5).toString());
        emailField.setText(tableModel.getValueAt(selectedRow, 6).toString());
    }

    private void clearForm() {
        nameField.setText("");
        cpfCnpjField.setText("");
        birthdateField.setText("");
        addressField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(
                this,
                message,
                "Erro",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        CustomerController customerController = new CustomerController();
        CustomerView customerView = new CustomerView(customerController);
        customerView.setVisible(true);
    }
}