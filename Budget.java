package Manager;

class Budget {
    private String category;
    private double limit;
    private double currentSpending;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
        this.currentSpending = 0;
    }

    public String getCategory() {
        return category;
    }

    public double getLimit() {
        return limit;
    }

    public double getCurrentSpending() {
        return currentSpending;
    }

    public void addExpense(double amount) {
        this.currentSpending += amount;
        checkBudget();
    }

    private void checkBudget() {
        if (this.currentSpending > this.limit) {
            System.out.println("Alert: You have exceeded your budget for " + this.category);
        }
    }
}

