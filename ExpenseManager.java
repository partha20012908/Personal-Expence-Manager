package Manager;

import java.util.HashMap;

class ExpenseManager {
    private HashMap<String, Budget> budgets;

    public ExpenseManager() {
        this.budgets = new HashMap<>();
    }

    public void setBudget(String category, double limit) {
        budgets.put(category, new Budget(category, limit));
    }

    public void addExpense(String category, double amount) {
        if (budgets.containsKey(category)) {
            budgets.get(category).addExpense(amount);
        } else {
            System.out.println("No budget set for category: " + category);
        }
    }

    public void printBudgets() {
        for (String category : budgets.keySet()) {
            Budget budget = budgets.get(category);
            System.out.println("Category: " + category + ", Limit: " + budget.getLimit() + ", Current Spending: " + budget.getCurrentSpending());
        }
    }
}

