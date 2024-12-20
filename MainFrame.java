package Manager;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MainFrame extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JTextField dateField, categoryField, descriptionField, amountField, budgetField;
    private JButton addButton, updateButton, deleteButton, viewSummaryButton, setBudgetButton, exitButton;
    private Map<String, Double> categoryBudgets = new HashMap<>();

    public MainFrame() {
        setTitle("Finance Manager");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initUI();
        connectToDatabase();
        loadExpenses();
    }

    private void initUI() {
        // Create a panel with GridBagLayout for form inputs
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(50, 50, 50)); // Dark gray background color

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel dateLabel = new JLabel("Date:");
        styleLabel(dateLabel);
        formPanel.add(dateLabel, gbc);

        gbc.gridx = 1;
        dateField = new JTextField(10);
        styleTextField(dateField);
        formPanel.add(dateField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel categoryLabel = new JLabel("Category:");
        styleLabel(categoryLabel);
        formPanel.add(categoryLabel, gbc);

        gbc.gridx = 1;
        categoryField = new JTextField(10);
        styleTextField(categoryField);
        formPanel.add(categoryField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel descriptionLabel = new JLabel("Description:");
        styleLabel(descriptionLabel);
        formPanel.add(descriptionLabel, gbc);

        gbc.gridx = 1;
        descriptionField = new JTextField(10);
        styleTextField(descriptionField);
        formPanel.add(descriptionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel amountLabel = new JLabel("Amount:");
        styleLabel(amountLabel);
        formPanel.add(amountLabel, gbc);

        gbc.gridx = 1;
        amountField = new JTextField(10);
        styleTextField(amountField);
        formPanel.add(amountField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        JLabel budgetLabel = new JLabel("Set Budget:");
        styleLabel(budgetLabel);
        formPanel.add(budgetLabel, gbc);

        gbc.gridx = 1;
        budgetField = new JTextField(10);
        styleTextField(budgetField);
        formPanel.add(budgetField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        addButton = new JButton("Add Expense");
        styleButton(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExpense();
            }
        });
        formPanel.add(addButton, gbc);

        gbc.gridy = 6;
        updateButton = new JButton("Update Expense");
        styleButton(updateButton);
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateExpense();
            }
        });
        formPanel.add(updateButton, gbc);

        gbc.gridy = 7;
        deleteButton = new JButton("Delete Expense");
        styleButton(deleteButton);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExpense();
            }
        });
        formPanel.add(deleteButton, gbc);

        gbc.gridy = 8;
        viewSummaryButton = new JButton("View Summary");
        styleButton(viewSummaryButton);
        viewSummaryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewSummary();
            }
        });
        formPanel.add(viewSummaryButton, gbc);

        gbc.gridy = 9;
        setBudgetButton = new JButton("Set Budget");
        styleButton(setBudgetButton);
        setBudgetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCategoryBudget();
            }
        });
        formPanel.add(setBudgetButton, gbc);

        gbc.gridy = 10;
        exitButton = new JButton("Exit");
        styleButton(exitButton);
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        formPanel.add(exitButton, gbc);

        add(formPanel, BorderLayout.WEST);

        // Table for displaying expenses
        model = new DefaultTableModel(new String[]{"ID", "Date", "Category", "Description", "Amount"}, 0);
        table = new JTable(model);
        table.setBackground(new Color(70, 70, 70)); // Dark gray background color
        table.setForeground(Color.WHITE); // White text color
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font
        table.setFillsViewportHeight(true);

        JScrollPane tableScrollPane = new JScrollPane(table);
        add(tableScrollPane, BorderLayout.CENTER);
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
        label.setForeground(Color.WHITE);
    }

    private void addExpense() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
            String sql = "INSERT INTO expenses (date, category, description, amount) VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dateField.getText());
            pstmt.setString(2, categoryField.getText());
            pstmt.setString(3, descriptionField.getText());
            pstmt.setDouble(4, Double.parseDouble(amountField.getText()));
            pstmt.executeUpdate();
            loadExpenses();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
                String sql = "UPDATE expenses SET date = ?, category = ?, description = ?, amount = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, dateField.getText());
                pstmt.setString(2, categoryField.getText());
                pstmt.setString(3, descriptionField.getText());
                pstmt.setDouble(4, Double.parseDouble(amountField.getText()));
                pstmt.setInt(5, id);
                pstmt.executeUpdate();
                loadExpenses();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void deleteExpense() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            int id = (int) table.getValueAt(selectedRow, 0);
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
                String sql = "DELETE FROM expenses WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                loadExpenses();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void viewSummary() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
            String sql = "SELECT category, SUM(amount) AS total_expense FROM expenses GROUP BY category";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            StringBuilder summary = new StringBuilder("Summary:\n");
            while (rs.next()) {
                String category = rs.getString("category");
                double totalExpense = rs.getDouble("total_expense");
                double budget = categoryBudgets.getOrDefault(category, 0.0);
                double remainingBudget = budget - totalExpense;
                summary.append(String.format("Category: %s, Total Expense: %.2f, Budget: %.2f, Remaining Budget: %.2f\n", category, totalExpense, budget, remainingBudget));
            }
            JOptionPane.showMessageDialog(this, summary.toString(), "Summary", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void setCategoryBudget() {
        try {
            String category = categoryField.getText();
            double budget = Double.parseDouble(budgetField.getText());
            categoryBudgets.put(category, budget);
            JOptionPane.showMessageDialog(this, "Budget set for category: " + category + " to: " + budget, "Budget Set", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid budget amount.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadExpenses() {
        model.setRowCount(0);
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finance_manager", "root", "root")) {
            String sql = "SELECT * FROM expenses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("date"));
                row.add(rs.getString("category"));
                row.add(rs.getString("description"));
                row.add(rs.getDouble("amount"));
                model.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}