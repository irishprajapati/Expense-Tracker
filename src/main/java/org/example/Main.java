package org.example;
import dao.*;
import model.*;
import validator.ExpenseValidator;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    //object creation
    static ExpenseDAO expenseDAO = new ExpenseDAO();
    //to get the user input
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while(true){
            System.out.println("\n --- Expense Tracker---");
            System.out.println("1. Add Expense: ");
            System.out.println("2. View All Expense: ");
            System.out.println("3. View By Category: ");
            System.out.println("4. Monthly Costs: ");
            System.out.println("5. Delete Expense: ");
            System.out.println("6. Exit: ");
            System.out.println("Choose: ");

            //reading choice of user:
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch(choice){
                case 1 -> addExpense(scanner);
                case 2 -> getAllExpenses();
                case 3 -> getExpenseByCategory(scanner);
                case 4 -> getMonthlyTotals();
                case 5-> deleteExpense(scanner);
                case 6-> {
                    System.out.println("Exiting..");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
            }
        }
    static void addExpense(Scanner scanner){
        System.out.println("Enter title: ");
        String title = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        //category menu with switch
        System.out.println("Select category: ");
        System.out.println("1. FOOD");
        System.out.println("2. TRANSPORT");
        System.out.println("3. RENT");
        System.out.println("4. ENTERTAINMENT");
        System.out.println("5. HEALTH CARE");
        System.out.println("6. OTHER");
        System.out.println("Choose: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        Category category = switch (categoryChoice){
            case 1 -> Category.FOOD;
            case 2 -> Category.TRANSPORT;
            case 3 -> Category.RENT;
            case 4 -> Category.ENTERTAINMENT;
            case 5 -> Category.HEALTHCARE;
            case 6 -> Category.OTHER;
            default -> null;
        };
        if(category == null ){
            System.out.println("Invalid category choice.");
            return;
        }
        Expense obj = new Expense(title, amount, category, LocalDateTime.now());
        ExpenseValidator validator = new ExpenseValidator();
        List<String> errors = validator.validate(obj);
        if(errors.isEmpty()){
            try{
                expenseDAO.addExpense(obj);

            }catch (SQLException ex){
                System.out.println("Database error: " + ex.getMessage());
            }

        }else{
            for(String error: errors){
                System.out.println(error);
            }
        }

    }

    static void getAllExpenses(){
        try{
            List<Expense> expenses = expenseDAO.getAllExpenses();
            if(expenses.isEmpty()){
                System.out.println("No expenses found");
                return;
            }
            System.out.println("Available expenses: ");
            for(Expense expense: expenses){
                System.out.println("ID: " + expense.getId());
                System.out.println("Title: " + expense.getTitle());
                System.out.println("Amount: " + expense.getAmount());
                System.out.println("Category: " + expense.getCategory());
                System.out.println("Date: " + expense.getDate());

            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static void getExpenseByCategory(Scanner scanner){
        System.out.println("Choose Category: ");
        System.out.println("1. FOOD");
        System.out.println("2. TRANSPORT");
        System.out.println("3. RENT");
        System.out.println("4. ENTERTAINMENT");
        System.out.println("5, HEALTHCARE");
        System.out.println("6. OTHER");
        System.out.println("CHOOSE: ");
        int categoryChoice = scanner.nextInt();
        scanner.nextLine();

        Category category = switch (categoryChoice){
            case 1 -> Category.FOOD;
            case 2 -> Category.TRANSPORT;
            case 3 -> Category.RENT;
            case 4 -> Category.ENTERTAINMENT;
            case 5 -> Category.HEALTHCARE;
            case 6 -> Category.OTHER;
            default -> null;
        };
        if(category == null){
            System.out.println("Invalid choice");
            return;
        }
        try {
            List<Expense> expenses = expenseDAO.getExpenseByCategory(category.name());
            if(expenses.isEmpty()){
                System.out.println("No expenses in this category");
                return;
            }
            System.out.println("Expenses by Category: ");
            for(Expense expense: expenses){
                System.out.println("ID: " + expense.getId());
                System.out.println("Title: " + expense.getTitle());
                System.out.println("Amount: " + expense.getAmount());
                System.out.println("Category: " + expense.getCategory());
                System.out.println("Date: " + expense.getDate());
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    static void getMonthlyTotals(){
    try{
        Map<String, Double> expenses = expenseDAO.getMonthlyTotals();
        if(expenses.isEmpty()){
            System.out.println("Not found");
            return;
        }
        for(Map.Entry<String, Double>entry: expenses.entrySet()){
            System.out.println("Month: " + entry.getKey() + "->" + entry.getValue());
        }

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
    }
    static void deleteExpense(Scanner scanner){
        getAllExpenses();
        System.out.println("Enter id to delete: ");
        int userChoice = scanner.nextInt();
        scanner.nextLine();
        try{
            expenseDAO.deleteExpense(userChoice);
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}
