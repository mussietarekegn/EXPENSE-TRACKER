import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;


public class Main extends Application {
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
