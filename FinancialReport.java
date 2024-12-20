package Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

class FinancialReport {
    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/finance_manager";
        String user = "root";
        String password = "root";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void generateExpenseSummary() {
        String query = "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category";
        StringBuilder result = new StringBuilder();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.append("Category: ").append(rs.getString("category")).append(", Total: ").append(rs.getDouble("total")).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, result.toString(), "Expense Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    public void generateMonthlySpendingTrends() {
        String query = "SELECT MONTH(date) as month, SUM(amount) AS total FROM expenses GROUP BY MONTH(date)";
        StringBuilder result = new StringBuilder();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                result.append("Month: ").append(rs.getInt("month")).append(", Total: ").append(rs.getDouble("total")).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, result.toString(), "Monthly Spending Trends", JOptionPane.INFORMATION_MESSAGE);
    }

    public void generateCategoryWiseSpending() {
        String query = "SELECT category, SUM(amount) AS total FROM expenses GROUP BY category";
        StringBuilder result = new StringBuilder();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            HashMap<String, Double> categorySpending = new HashMap<>();
            while (rs.next()) {
                categorySpending.put(rs.getString("category"), rs.getDouble("total"));
            }

            for (String category : categorySpending.keySet()) {
                result.append("Category: ").append(category).append(", Total: ").append(categorySpending.get(category)).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, result.toString(), "Category-wise Spending", JOptionPane.INFORMATION_MESSAGE);
    }
}