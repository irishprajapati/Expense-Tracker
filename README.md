# Expense Tracker
A console-based Expense Tracker built in Java using Maven, JDBC, and PostgreSQL. No frameworks, no ORM — raw SQL, manual validation, and clean layer separation.

## Purpose
Built as a structured stepping stone toward Spring Boot. Every pattern here maps directly to how Spring organizes things — just without the magic:

| This project         | Spring Boot equivalent         |
|----------------------|-------------------------------|
| `ExpenseValidator`   | `@Valid` + `@NotBlank`        |
| `ExpenseDAO`         | `@Repository`                 |
| `DBConnection`       | `application.properties`      |
| `App.java` menu      | `@Controller`                 |
| `Category` enum      | `@Enumerated(EnumType.STRING)`|

## Tech Stack
- Java 17+
- Maven
- JDBC
- PostgreSQL
- No external libraries except the PostgreSQL driver

## Features
- Add an expense with category selection
- View all expenses
- View expenses by category
- View total spent per month
- Delete an expense by id
- Input validation before any DB operation

## Validation Rules
Enforced by `ExpenseValidator` before any data reaches the database:
- Title cannot be null or blank
- Amount must be greater than zero
- Category must be selected
- Date cannot be a future date

If any rule fails, all errors are shown at once and the database is never touched.

## How to Run
**1. Clone the repo**
```bash
git clone https://github.com/yourusername/expense-tracker.git
cd expense-tracker
```

**2. Set up the database**
Create a PostgreSQL database and run the schema above.

**3. Configure the connection**
Update `DBConnection.java` with your credentials:
```java
private static final String URL = "jdbc:postgresql://localhost:5432/your_db";
private static final String USER = "your_user";
private static final String PASSWORD = "your_password";
```

**4. Compile**
```bash
mvn compile
```

**5. Run**
```bash
mvn exec:java -Dexec.mainClass="com.expensetracker.app.App"
```

## What This Teaches
- Writing raw SQL through `PreparedStatement`
- Reading results with `ResultSet`
- Aggregate queries — `SUM` and `GROUP BY` from Java
- Manual input validation returning a `List<String>` of errors
- Enum handling across Java and PostgreSQL
- Clean layer separation — `App` never touches SQL, `DAO` never touches `Scanner`

## Validation Flow
