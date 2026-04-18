package org.example;
import dao.*;
import model.*;
import validator.ExpenseValidator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter title: ");
        String title = scanner.nextLine();

        System.out.println("Enter amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Select category:");
        System.out.println("1. FOOD");
        System.out.println("2. TRANSPORT");
        System.out.println("3. RENT");
        System.out.println("4. ENTERTAINMENT");
        System.out.println("5. HEALTHCARE");
        System.out.println("6. OTHER");
        System.out.print("Choose: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        Category category = switch(choice){
            case 1 -> Category.FOOD;
            case 2 -> Category.TRANSPORT;
            case 3 -> Category.RENT;
            case 4 -> Category.ENTERTAINMENT;
            case 5 -> Category.HEALTHCARE;
            case 6 -> Category.OTHER;
            default -> null; // invalid choice
        };

        if(category == null){
            System.out.println("Invalid category selected.");
            return; // go back to menu
        }

        Expense e = new Expense(title, amount, category, LocalDateTime.now());

        ExpenseValidator validator = new ExpenseValidator();
        List<String> errors = validator.validate(e);
        if(errors.isEmpty()){
            ExpenseDAO.addExpense(e);
        }else{
            System.out.println("Couldnot add expense. Fix the following: ");
            for(String error: errors){
                System.out.println(" -" + error);
            }
        }
    }
}
