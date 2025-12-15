## Expense Tracker with File Persistence

This JavaFX Expense Tracker now stores expenses in a CSV file (`expenses.csv`) to persist data between sessions.  

### Features
- Add, view, and delete expenses
- Monthly summary and category-wise totals
- Bar chart for visual expense analysis
- Automatic saving and loading of expenses from file
- Totals and charts update in real-time when adding or deleting expenses

### File Storage
- Expenses are saved in `expenses.csv` in the format: `amount,category,date`
- On startup, the app loads all existing expenses from the file
- Any changes (add/delete) are automatically written to the file
