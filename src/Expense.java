import java.time.LocalDate;

public class Expense {
    private double amount;
    private String category;
    private LocalDate date;

    // Constructor
    public Expense(double amount, String category, LocalDate date) {
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    // Getters
    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDate getDate() {
        return date;
    }

    // To String method (for easy display)
    @Override
    public String toString() {
        return category + ": $" + amount + " on " + date;
    }
}

