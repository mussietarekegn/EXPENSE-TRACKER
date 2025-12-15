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

    private ArrayList<Expense> expenses = new ArrayList<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        // ===== Title =====
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

        // ===== Total Labels =====
        Label totalLabel = new Label("Total Spent: $0");

        Label foodTotal = new Label("Food: $0");
        Label transportTotal = new Label("Transport: $0");
        Label shoppingTotal = new Label("Shopping: $0");
        Label billsTotal = new Label("Bills: $0");
        Label otherTotal = new Label("Other: $0");

        VBox categoryTotals = new VBox(8,
                foodTotal,
                transportTotal,
                shoppingTotal,
                billsTotal,
                otherTotal
        );
        categoryTotals.setPadding(new Insets(10));

        // ===== Expense Form =====
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        ComboBox<String> categoryBox = new ComboBox<>();
        categoryBox.getItems().addAll("Food", "Transport", "Shopping", "Bills", "Other");
        categoryBox.setPromptText("Category");

        DatePicker datePicker = new DatePicker();

        Button addButton = new Button("Add Expense");

        addButton.setOnAction(e -> {
            double amount = Double.parseDouble(amountField.getText());
            String category = categoryBox.getValue();
            LocalDate date = datePicker.getValue();

            Expense newExpense = new Expense(amount, category, date);
            expenses.add(newExpense);
            table.getItems().add(newExpense);

            double total = 0;
            double food = 0, transport = 0, shopping = 0, bills = 0, other = 0;

            for (Expense exp : expenses) {
                total += exp.getAmount();

                switch (exp.getCategory()) {
                    case "Food" -> food += exp.getAmount();
                    case "Transport" -> transport += exp.getAmount();
                    case "Shopping" -> shopping += exp.getAmount();
                    case "Bills" -> bills += exp.getAmount();
                    case "Other" -> other += exp.getAmount();
                }
            }

            totalLabel.setText("Total Spent: $" + total);
            foodTotal.setText("Food: $" + food);
            transportTotal.setText("Transport: $" + transport);
            shoppingTotal.setText("Shopping: $" + shopping);
            billsTotal.setText("Bills: $" + bills);
            otherTotal.setText("Other: $" + other);

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
        root.setRight(categoryTotals);
        root.setBottom(totalLabel);
        BorderPane.setMargin(totalLabel, new Insets(10));

        Scene scene = new Scene(root, 800, 400);
        stage.setTitle("Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
