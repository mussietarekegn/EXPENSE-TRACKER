import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.util.Callback;

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

        // ===== Total & Category Labels =====
        Label totalLabel = new Label("Total Spent: $0");
        Label foodTotal = new Label("Food: $0");
        Label transportTotal = new Label("Transport: $0");
        Label shoppingTotal = new Label("Shopping: $0");
        Label billsTotal = new Label("Bills: $0");
        Label otherTotal = new Label("Other: $0");

        VBox categoryTotals = new VBox(8, foodTotal, transportTotal, shoppingTotal, billsTotal, otherTotal);
        categoryTotals.setPadding(new Insets(10));

        // ===== Monthly Summary =====
        Label monthlyTitle = new Label("Monthly Summary");
        monthlyTitle.setStyle("-fx-font-weight: bold;");

        ComboBox<String> monthBox = new ComboBox<>();
        monthBox.getItems().addAll(
                "JANUARY", "FEBRUARY", "MARCH", "APRIL",
                "MAY", "JUNE", "JULY", "AUGUST",
                "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"
        );
        monthBox.setPromptText("Select Month");

        Label monthlyTotal = new Label("Monthly Total: $0");
        VBox monthlyBox = new VBox(8, monthlyTitle, monthBox, monthlyTotal);
        monthlyBox.setPadding(new Insets(10));

        // ===== Bar Chart =====
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Category");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Amount");

        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Category-wise Expenses");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Expenses");
        barChart.getData().add(series);
        barChart.setPrefHeight(250);

        // ===== Helper method to recalc totals =====
        Runnable recalcTotals = () -> {
            double total = 0;
            double food = 0, transport = 0, shopping = 0, bills = 0, other = 0;
            double monthSum = 0;

            for (Expense exp : expenses) {
                total += exp.getAmount();
                switch (exp.getCategory()) {
                    case "Food" -> food += exp.getAmount();
                    case "Transport" -> transport += exp.getAmount();
                    case "Shopping" -> shopping += exp.getAmount();
                    case "Bills" -> bills += exp.getAmount();
                    case "Other" -> other += exp.getAmount();
                }
                if (monthBox.getValue() != null &&
                        exp.getDate().getMonth().toString().equals(monthBox.getValue())) {
                    monthSum += exp.getAmount();
                }
            }

            totalLabel.setText("Total Spent: $" + total);
            foodTotal.setText("Food: $" + food);
            transportTotal.setText("Transport: $" + transport);
            shoppingTotal.setText("Shopping: $" + shopping);
            billsTotal.setText("Bills: $" + bills);
            otherTotal.setText("Other: $" + other);
            monthlyTotal.setText("Monthly Total: $" + monthSum);

            series.getData().clear();
            series.getData().add(new XYChart.Data<>("Food", food));
            series.getData().add(new XYChart.Data<>("Transport", transport));
            series.getData().add(new XYChart.Data<>("Shopping", shopping));
            series.getData().add(new XYChart.Data<>("Bills", bills));
            series.getData().add(new XYChart.Data<>("Other", other));
        };

        // ===== Delete Column =====
        TableColumn<Expense, Void> deleteCol = new TableColumn<>("Action");
        Callback<TableColumn<Expense, Void>, TableCell<Expense, Void>> cellFactory = param -> {
            final TableCell<Expense, Void> cell = new TableCell<>() {
                private final Button btn = new Button("Delete");

                {
                    btn.setOnAction(event -> {
                        Expense exp = getTableView().getItems().get(getIndex());
                        expenses.remove(exp);
                        table.getItems().remove(exp);
                        recalcTotals.run(); // ✅ Works now
                    });
                }

                @Override
                public void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : btn);
                }
            };
            return cell;
        };
        deleteCol.setCellFactory(cellFactory);

        table.getColumns().addAll(amountCol, categoryCol, dateCol, deleteCol);

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

            recalcTotals.run();

            amountField.clear();
            categoryBox.setValue(null);
            datePicker.setValue(null);
        });

        monthBox.setOnAction(e -> recalcTotals.run());

        VBox form = new VBox(10, amountField, categoryBox, datePicker, addButton);
        form.setPadding(new Insets(10));

        VBox rightPanel = new VBox(20, categoryTotals, monthlyBox);

        BorderPane root = new BorderPane();
        root.setTop(title);
        BorderPane.setAlignment(title, Pos.CENTER);
        root.setLeft(form);
        root.setCenter(table);
        root.setRight(rightPanel);
        root.setBottom(new VBox(10, totalLabel, barChart));
        BorderPane.setMargin(totalLabel, new Insets(10));

        Scene scene = new Scene(root, 950, 500);
        stage.setTitle("Expense Tracker");
        stage.setScene(scene);
        stage.show();
    }
}
