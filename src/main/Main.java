package main;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.cashflow.CashFlowIn;
import main.cashflow.RecurringCashFlowOut;
import main.goals.SavingGoals;
import main.persistence.GSON;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    private static List<Transactions> transactionsList;
    private Stage primaryStage;
    private Scene openScreen, ccStatementScene, incomeScene, recurringBillScene, savingGoalScene, goalDistributionScene;

    private final int verticalPadding = 20;
    private final int screenWidth = 500;
    private final int screenHeight = 500;

    private double amountContributedThisMonth = 0;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button addTransactions, displayTransaction, displayCCdetails, goalContributions;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Budgeting App");

        primaryStage.setOnCloseRequest(e -> closeProgram());
        addTransactions = new Button("Add Transactions");
        addTransactions.setOnAction(e -> toAddScene());
        displayTransaction = new Button("Transaction Details");
        displayTransaction.setOnAction(e -> toMoreInfoScene());
        displayCCdetails = new Button("Graph of CC Statements");
        displayCCdetails.setOnAction(e -> toSelectGraphScene());
        goalContributions = new Button("Distribute Remaining Income to Goals");
        goalContributions.setOnAction(e -> toGoalDistributionScene());

        //load previous data
        transactionsList = GSON.read();

        VBox openingLayout = new VBox(verticalPadding);
        openingLayout.getChildren().addAll(addTransactions, displayTransaction, displayCCdetails, goalContributions);
        openingLayout.setAlignment(Pos.CENTER);

        openScreen = new Scene(openingLayout, screenWidth, screenHeight);
        primaryStage.setScene(openScreen);
        primaryStage.show();
    }


    private void toMoreInfoScene() {
        Scene moreInfoScene;
        List<String> allItems = new LinkedList<>();
        Label l = new Label("Select Category Then Transaction from Drop-downs");

        for (int i = 0; i < transactionsList.size(); i++) {
            allItems.add(transactionsList.get(i).getDescription());
        }

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(allItems);
        comboBox.setPromptText("choose transaction");
        comboBox.setVisibleRowCount(8);

        Button select = new Button("Select");
        select.setOnAction(e -> toDetailsScene(comboBox.getValue()));

        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox moreInfoLayout = new VBox(verticalPadding);
        moreInfoLayout.getChildren().addAll(l, comboBox, select, home);
        moreInfoLayout.setAlignment(Pos.CENTER);

        moreInfoScene = new Scene(moreInfoLayout, screenWidth, screenHeight);
        primaryStage.setScene(moreInfoScene);
        primaryStage.show();
    }

    private void toDetailsScene(String s) {
        Transactions moreInfo = null;
        for (Transactions t : transactionsList) {
            if (t.getDescription().equals(s)) {
                moreInfo = t;
            }
        }

        Label l = new Label(moreInfo.toString());

        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox detailsLayout = new VBox(verticalPadding);
        detailsLayout.getChildren().addAll(l, home);
        detailsLayout.setAlignment(Pos.CENTER);

        Scene detailsScene = new Scene(detailsLayout, screenWidth, screenHeight);
        primaryStage.setScene(detailsScene);
        primaryStage.show();

    }

    private void toAddScene() {
        Button addFromCCStatement, addIncomeSource, addRecurringBills, addSavingGoal;
        addFromCCStatement = new Button("Credit Card Statement");
        addFromCCStatement.setOnAction(e -> toCCStatementScene());
        addIncomeSource = new Button("Source of Income");
        addIncomeSource.setOnAction(e -> toIncomeScene());
        addRecurringBills = new Button("Recurring Bills");
        addRecurringBills.setOnAction(e -> toRecurringBillScene());
        addSavingGoal = new Button("Saving Goal");
        addSavingGoal.setOnAction(e -> toSavingGoalScene());
        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox addLayout = new VBox(verticalPadding);
        addLayout.setAlignment(Pos.CENTER);
        addLayout.getChildren().addAll(addFromCCStatement, addIncomeSource, addRecurringBills, addSavingGoal, home);

        Scene addScene = new Scene(addLayout, screenWidth, screenHeight);
        primaryStage.setScene(addScene);
        primaryStage.show();

    }

    private void toCCStatementScene() {
        Label descriptionLabel = new Label("Description:");
        TextField description = new TextField();
        Label locationLabel = new Label("File Location:");
        TextField location = new TextField();

        Button addCCStatement = new Button("Add");
        addCCStatement.setOnAction(e -> {
            try {
                Purchases p = new Purchases(location.getText(), description.getText());
                transactionsList.add(p);
                primaryStage.setScene(openScreen);
            } catch (FileNotFoundException ex) {
                errorScene("Sorry, could not find file", ccStatementScene);
            }
        });

        VBox ccStatementLayout = new VBox(verticalPadding);
        ccStatementLayout.setAlignment(Pos.CENTER);
        ccStatementLayout.getChildren().addAll(descriptionLabel, description, locationLabel, location, addCCStatement);

        ccStatementScene = new Scene(ccStatementLayout, screenWidth, screenHeight);
        primaryStage.setScene(ccStatementScene);
        primaryStage.show();

    }


    private void toIncomeScene() {
        Label descriptionLabel = new Label("Description");
        TextField description = new TextField();
        Label amountLabel = new Label("Amount per Month");
        TextField amount = new TextField();
        Label tagsLabel = new Label("Identifying Tags - separate with semi-colon");
        TextField tags = new TextField();

        Button addIncome = new Button("Add");
        addIncome.setOnAction(e -> {
            try {
                double doubleAmount = Double.valueOf(amount.getText());
                CashFlowIn income = new CashFlowIn(description.getText(), doubleAmount);
                String[] tagsToAdd = tags.getText().split(";");
                if (tagsToAdd.length != 0) {
                    for (String s : tagsToAdd) {
                        income.addTag(s);
                    }
                }
                transactionsList.add(income);
                primaryStage.setScene(openScreen);
            } catch (NumberFormatException d) {
                errorScene("Sorry, amount could not be read", incomeScene);
            }
        });

        VBox incomeLayout = new VBox(verticalPadding);
        incomeLayout.setAlignment(Pos.CENTER);
        incomeLayout.getChildren().addAll(descriptionLabel, description, amountLabel, amount, tagsLabel, tags, addIncome);

        incomeScene = new Scene(incomeLayout, screenWidth, screenHeight);
        primaryStage.setScene(incomeScene);
        primaryStage.show();
    }


    private void toRecurringBillScene() {
        Label descriptionLabel = new Label("Description");
        TextField description = new TextField();
        Label amountLabel = new Label("Amount per Month");
        TextField amount = new TextField();
        Label tagsLabel = new Label("Identifying Tags - separate with semi-colon");
        TextField tags = new TextField();

        Button addBill = new Button("Add");
        addBill.setOnAction(e -> {
            try {
                double doubleAmount = Double.valueOf(amount.getText());
                RecurringCashFlowOut expense = new RecurringCashFlowOut(description.getText(), doubleAmount);
                String[] tagsToAdd = tags.getText().split(";");
                if (tagsToAdd.length != 0) {
                    for (String s : tagsToAdd) {
                        expense.addTag(s);
                    }
                }
                transactionsList.add(expense);
                primaryStage.setScene(openScreen);
            } catch (NumberFormatException d) {
                errorScene("Sorry, amount could not be read", incomeScene);
            }
        });

        VBox recurringBillLayout = new VBox(verticalPadding);
        recurringBillLayout.setAlignment(Pos.CENTER);
        recurringBillLayout.getChildren().addAll(descriptionLabel, description, amountLabel, amount, tagsLabel, tags, addBill);

        recurringBillScene = new Scene(recurringBillLayout, screenWidth, screenHeight);
        primaryStage.setScene(recurringBillScene);
        primaryStage.show();
    }

    private void toSavingGoalScene() {
        Label descriptionLabel = new Label("Description");
        TextField description = new TextField();
        Label amountLabel = new Label("Amount");
        TextField amount = new TextField();
        Label tagsLabel = new Label("Identifying Tags - separate with semi-colon");
        TextField tags = new TextField();

        Button addGoal = new Button("Add");
        addGoal.setOnAction(e -> {
            try {
                double doubleAmount = Double.valueOf(amount.getText());
                SavingGoals goal = new SavingGoals(description.getText(), doubleAmount);
                String[] tagsToAdd = tags.getText().split(";");
                if (tagsToAdd.length != 0) {
                    for (String s : tagsToAdd) {
                        goal.addTag(s);
                    }
                }
                transactionsList.add(goal);
                primaryStage.setScene(openScreen);
            } catch (NumberFormatException d) {
                errorScene("Sorry, amount could not be read", incomeScene);
            }
        });

        VBox savingLayout = new VBox(verticalPadding);
        savingLayout.setAlignment(Pos.CENTER);
        savingLayout.getChildren().addAll(descriptionLabel, description, amountLabel, amount, tagsLabel, tags, addGoal);

        savingGoalScene = new Scene(savingLayout, screenWidth, screenHeight);
        primaryStage.setScene(savingGoalScene);
        primaryStage.show();

    }

    private void errorScene(String msg, Scene scene) {
        Label l = new Label(msg + "\n" +
                "Please try again");
        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction(d -> {
            if (scene.equals(ccStatementScene)) {
                toCCStatementScene();
            }
            if (scene.equals(incomeScene)) {
                toIncomeScene();
            }
            if (scene.equals(recurringBillScene)) {
                toRecurringBillScene();
            }
            if (scene.equals(savingGoalScene)) {
                toSavingGoalScene();
            }
        });

        VBox tryAgainLayout = new VBox(verticalPadding);
        tryAgainLayout.setAlignment(Pos.CENTER);
        tryAgainLayout.getChildren().addAll(l, tryAgain);

        Scene errorScene = new Scene(tryAgainLayout, screenWidth, screenHeight);
        primaryStage.setScene(errorScene);
        primaryStage.show();
    }

    private void toSelectGraphScene() {
        List<String> allItems = new LinkedList<>();
        for (int i = 0; i < transactionsList.size(); i++) {
            if (transactionsList.get(i) instanceof Purchases) {
                allItems.add(transactionsList.get(i).description);
            }
        }

        Label l = new Label("Select CreditCard Statement from Dropdown for More Information");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(allItems);
        comboBox.setPromptText("choose one");
        comboBox.setVisibleRowCount(8);

        Button select = new Button("Select");
        select.setOnAction(e -> toGraphScene(comboBox.getValue()));

        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox selectGraphLayout = new VBox(verticalPadding);
        selectGraphLayout.setAlignment(Pos.CENTER);
        selectGraphLayout.getChildren().addAll(l, comboBox, select, home);

        Scene selectGraphScene = new Scene(selectGraphLayout, screenWidth, screenHeight);
        primaryStage.setScene(selectGraphScene);
        primaryStage.show();
    }

    private void toGraphScene(String s) {
        Purchases moreInfo = null;
        for (Transactions t : transactionsList) {
            if (t.getDescription().equals(s) && t instanceof Purchases) {
                moreInfo = (Purchases) t;
                break;
            }
        }


        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        for (String category : moreInfo.getCategories()) {
            pieChartData.add(new PieChart.Data(category, moreInfo.CategoryTotal(category)));
        }

        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle(moreInfo.description);

        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox graphLayout = new VBox(verticalPadding);
        graphLayout.setAlignment(Pos.CENTER);
        graphLayout.getChildren().addAll(chart, home);

        Scene graphScene = new Scene(graphLayout, screenWidth, screenHeight);
        primaryStage.setScene(graphScene);
        primaryStage.show();

    }

    private void toGoalDistributionScene() {
        //get all savingGoals
        List<SavingGoals> goals = new LinkedList<>();
        for (Transactions t: transactionsList) {
            if (t.getStatus().equals(Status.TO_PURCHASE)
                && t instanceof SavingGoals) {
                goals.add((SavingGoals) t);
            }
        }
        //put into list for comboBox
        List<String> goalDescriptions = new LinkedList<>();
        for (SavingGoals s: goals) {
            goalDescriptions.add((s.getDescription()));
        }

        Label amountToDistribute = new Label("Income Left to Distribute: $" + incomeRemaining());

        ComboBox<String> savingsGoalsOptions = new ComboBox<>();
        savingsGoalsOptions.getItems().addAll(goalDescriptions);
        savingsGoalsOptions.setPromptText("choose item to contribute to");
        savingsGoalsOptions.setVisibleRowCount(8);

        TextField amountToContribute = new TextField();

        Button contribute = new Button("Contribute");
        contribute.setOnAction(e -> makeContribution(savingsGoalsOptions.getValue(), amountToContribute.getText()));

        Button home = new Button("Home");
        home.setOnAction(e -> primaryStage.setScene(openScreen));

        VBox goalDistributionLayout = new VBox(verticalPadding);
        goalDistributionLayout.getChildren().addAll(amountToDistribute, savingsGoalsOptions, amountToContribute, contribute, home);
        goalDistributionLayout.setAlignment(Pos.CENTER);


        goalDistributionScene = new Scene(goalDistributionLayout, screenWidth, screenHeight);
        primaryStage.setScene(goalDistributionScene);
        primaryStage.show();

    }

    private double incomeRemaining() {
        double amountRemaining = 0;
        //add in most recent monthly bill
        for (int i = transactionsList.size() - 1; i > 0; i--) {
            if(transactionsList.get(i).getStatus().equals(Status.CREDITCARD_BILL)
                    && transactionsList.get(i) instanceof Purchases) {
                amountRemaining += ((Purchases) transactionsList.get(i)).totalBill();
                break;
            }
        }

        //add in recurring expenses and income
        for (Transactions t: transactionsList) {
            if(t.getStatus().equals(Status.RECURRING_BILLS)
                && t instanceof RecurringCashFlowOut) {
                amountRemaining += t.getAmount();
            }
            if(t.getStatus().equals(Status.INCOME)
                    && t instanceof CashFlowIn) {
                amountRemaining += t.getAmount();
            }
        }

        amountRemaining -= amountContributedThisMonth;

        return (double) Math.round(amountRemaining * 100.0)/100.0;
    }

    private void makeContribution(String value, String text) {
        SavingGoals chosenItem = null;
        for (Transactions t: transactionsList) {
            if (t.getDescription().equals(value)
                && t instanceof  SavingGoals) {
                chosenItem = (SavingGoals) t;
            }
        }
        try {
            double contributionAmount = Double.valueOf(text);
            amountContributedThisMonth += contributionAmount;
            chosenItem.contribution(contributionAmount);
            primaryStage.setScene(openScreen);
            primaryStage.show();
        } catch (NumberFormatException e) {
            errorScene("Invalid Amount Entered", goalDistributionScene);
        }

    }

    private void closeProgram() {
        GSON.write(transactionsList);
        primaryStage.close();
    }

}