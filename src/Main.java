import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main extends Application {
    private ArrayList<Expense> expenses = new ArrayList<>();  // List to store expenses

    public static void main(String[] args) {
        launch(args);  // Starts JavaFX application
    }

    @Override
    public void start(Stage stage) {

        // Title
        Label title = new Label("Expense Tracker");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // ===== Expense Form =====
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Food", "Transport", "Shopping", "Bills", "Other");
        categoryBox.setPromptText("Category");

        DatePicker datePicker = new DatePicker();

        Button addButton = new Button("Add Expense");

        // Button action to add expense
        addButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryBox.getValue();
            LocalDate date = datePicker.getValue();

            // Create an Expense object and add it to the list
            Expense newExpense = new Expense(amount, category, date);
            expenses.add(newExpense);
            System.out.println("Added: " + newExpense);
        });

        VBox form = new VBox(10, amountField, categoryBox, datePicker, addButton);
        form.setPadding(new Insets(10));

        // ===== Expense Table =====
        TableView<String> table = new TableView<>();
        table.setPlaceholder(new Label("No expenses yet"));

        // ===== Bottom Total =====
        Label totalLabel = new Label("Total Spent: $0.00");

        // ===== Layout =====
        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);

        root.setLeft(form);
        root.setCenter(table);
        root.setBottom(totalLabel);
        BorderPane.setMargin(totalLabel, new Insets(10));

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
