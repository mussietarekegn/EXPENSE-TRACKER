import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.cell.PropertyValueFactory;


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

        // ===== Expense Table =====
        TableView<Expense> table = new TableView<>();
        table.setPlaceholder(new Label("No expenses yet"));

        TableColumn<Expense, Double> amountCol = new TableColumn<>("Amount");
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));

        TableColumn<Expense, String> categoryCol = new TableColumn<>("Category");
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));

        TableColumn<Expense, LocalDate> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        table.getColumns().addAll(amountCol, categoryCol, dateCol);

        // ===== Bottom Total =====
        Label totalLabel = new Label("Total Spent: $0.00");

        // ===== Expense Form =====
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Food", "Transport", "Shopping", "Bills", "Other");
        categoryBox.setPromptText("Category");

        DatePicker datePicker = new DatePicker();

        Button addButton = new Button("Add Expense");

        // Button action
        addButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryBox.getValue();
            LocalDate date = datePicker.getValue();

            Expense newExpense = new Expense(amount, category, date);
            expenses.add(newExpense);
            table.getItems().add(newExpense);

            double total = 0;
            for (Expense exp : expenses) {
                total += exp.getAmount();
            }
            totalLabel.setText("Total Spent: $" + total);

            amountField.clear();
            categoryBox.setValue(null);
            datePicker.setValue(null);
        });

        VBox form = new VBox(10, amountField, categoryBox, datePicker, addButton);
        form.setPadding(new Insets(10));

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
