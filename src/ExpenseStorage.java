import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class ExpenseStorage {

    private static final String FILE_NAME = "expenses.csv";

    // Write all expenses to file (overwrite mode)
    public static void saveExpenses(ArrayList<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense exp : expenses) {
                String line = exp.getAmount() + "," + exp.getCategory().trim() + "," + exp.getDate();
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving expenses: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Optional: Append a single expense to file
    public static void appendExpense(Expense exp) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            String line = exp.getAmount() + "," + exp.getCategory().trim() + "," + exp.getDate();
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error appending expense: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Read expenses from file
    public static ArrayList<Expense> loadExpenses() {
        ArrayList<Expense> expenses = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return expenses; // file doesn't exist yet

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    try {
                        double amount = Double.parseDouble(parts[0].trim());
                        String category = parts[1].trim();
                        LocalDate date = LocalDate.parse(parts[2].trim());
                        expenses.add(new Expense(amount, category, date));
                    } catch (Exception ex) {
                        System.err.println("Skipping invalid line: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading expenses: " + e.getMessage());
            e.printStackTrace();
        }
        return expenses;
    }
}
