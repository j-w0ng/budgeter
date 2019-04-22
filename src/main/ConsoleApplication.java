package main;

import main.cashflow.RecurringCashFlowOut;
import main.goals.SavingGoals;
import main.persistence.GSON;

import java.util.List;
import java.util.Scanner;

public class ConsoleApplication {

    private static List<Transactions> transactionsList;
    private static Scanner input;
    private static String userInput;
    private static boolean exit = false;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        transactionsList = GSON.read();

        while (!exit) {
            displayTransactions();
            userInput = input.nextLine();
            takeActionBasedOnInput();
        }
    }

    private static void takeActionBasedOnInput() {
        switch (userInput.toUpperCase().charAt(0)) {
            case 'A': addNewTransaction();
                break;
            case 'D': displayTransactionDetails();
                break;
            case 'Q':
                GSON.write(transactionsList);
                exit = true;
                break;
            default: System.out.println("Invalid input!");
        }
    }

    private static void displayTransactionDetails() {
        for (Transactions t: transactionsList) {
            System.out.println(t);
        }
        pressEnterToContinue();
    }

    private static void pressEnterToContinue() {
        System.out.print("\nPress enter to continue ... ");
        input.nextLine();
    }


    private static void addNewTransaction() {
        System.out.println("Enter Transaction Type: \n" +
                            "1. CC Statement\n" +
                            "2. Income Recurring Expense\n" +
                            "3. Saving Goal");
        System.out.print("> ");
        userInput = input.nextLine();
        if (userInput.length() == 0) {
            System.out.println("Please select transaction type!");
        }
        switch (userInput.toUpperCase()) {
            case "CC STATEMENT": addStatement();
                break;
            case "INCOME RECURRING EXPENSE": addExpense();
                break;
            case "SAVING GOAL": addSavingGoal();
                break;
            case "1": addStatement();
                break;
            case "2": addExpense();
                break;
            case "3": addSavingGoal();
                break;
            default:
                System.out.println("Invalid Input");
        }
    }

    private static void addExpense() {
        System.out.println("Please enter description and amount (e.g. description; amount)");
        System.out.print("> ");
        userInput = input.nextLine();
        String s = userInput;
        String description = s.substring(0, s.indexOf(";"));
        Double amount = Double.valueOf(s.substring(s.indexOf(";") + 1));
        RecurringCashFlowOut saving = new RecurringCashFlowOut(description, amount);
        transactionsList.add(saving);
    }

    private static void addStatement() {
        System.out.println("Please enter description and location of csv file (e.g. description; location)");
        System.out.print("> ");
        userInput = input.nextLine();
        String s = userInput;
        String description = s.substring(0, s.indexOf(";"));
        String location = s.substring(s.indexOf(";") + 1);
        Purchases p = new Purchases(location, description);
        transactionsList.add(p);
    }

    private static void addSavingGoal() {
        System.out.println("Please enter description and amount (e.g. description; amount)");
        System.out.print("> ");
        userInput = input.nextLine();
        String s = userInput;
        String description = s.substring(0, s.indexOf(";"));
        Double amount = Double.valueOf(s.substring(s.indexOf(";") + 1));
        SavingGoals saving = new SavingGoals(description, amount);
        transactionsList.add(saving);
    }

    private static void displayTransactions() {
        System.out.println("\n");
        System.out.println("------------------------------");
        System.out.println("\tMy Budgetting App");
        System.out.println("------------------------------");
        displayPendingTasks();
        displayMenu();
    }

    private static void displayMenu() {
        System.out.println("You can ...");
        System.out.println("\tEnter A to add Transaction;");
        System.out.println("\tEnter D to show previous Transactions;");
        System.out.println("\tEnter Q to quit.");
        System.out.print("> ");
    }

    private static void displayPendingTasks() {
        if (transactionsList.isEmpty()) {
            System.out.println("Add some transactions!");
        } else {
            displayTasks(transactionsList);
        }
        System.out.println("------------------------------");
    }

    private static void displayTasks(List<Transactions> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            System.out.printf("%3d. %s\n", i + 1, transactions.get(i).getDescription());
        }
    }
}
