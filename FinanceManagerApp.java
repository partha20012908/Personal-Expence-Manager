package Manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

class FinanceManagerApp {
    public static Connection connect() {
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

    public static void addExpenseToDatabase(String category, double amount) {
        String query = "INSERT INTO expenses (category, amount, date) VALUES (?, ?, NOW())";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, category);
            pstmt.setDouble(2, amount);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

