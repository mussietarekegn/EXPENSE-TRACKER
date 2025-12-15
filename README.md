## Expense Tracker with File Persistence

This JavaFX Expense Tracker now stores expenses in a CSV file (`expenses.csv`) to persist data between sessions.

### Features

* Add, view, and delete expenses
* Monthly summary and category-wise totals
* Bar chart for visual expense analysis
* Automatic saving and loading of expenses from file
* Totals and charts update in real-time when adding or deleting expenses
* Chart defaults to all-time totals and updates based on selected month

### File Storage

* Expenses are saved in `expenses.csv` in the format: `amount,category,date`
* On startup, the app loads all existing expenses from the file
* Any changes (add/delete) are automatically written to the file

### Usage

1. Enter **Amount**, **Category**, and **Date** in the form and click **Add Expense**
2. Delete an expense by clicking the **Delete** button in the table
3. Select a **Month** to filter totals and chart for that month
4. View category-wise expenses in the bar chart

### Categories

* Food
* Transport
* Shopping
* Bills
* Other

### Requirements

* Java 17+
* JavaFX 17+ properly configured

### Notes

* The bar chart shows all-time totals by default.
* The monthly selection dynamically updates totals and chart data.
