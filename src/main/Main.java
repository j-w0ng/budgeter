package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private Scene openScreen, addScene, moreInfoScene, ccStatementScene, errorScene,
            detailsScene, incomeScene, recurringBillScene, savingGoalScene;

    private final int verticalPadding = 20;
    private final int screenWidth = 500;
    private final int screenHeight = 500;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button addTransactions, displayTransaction;
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Budgeting App");

        primaryStage.setOnCloseRequest(e -> closeProgram());
        addTransactions = new Button("Add Transactions");
        addTransactions.setOnAction(e -> toAddScene());
        displayTransaction = new Button("Transaction Details");
        displayTransaction.setOnAction(e -> toMoreInfoScene());

        //load previous data
        transactionsList = GSON.read();

        VBox openingLayout = new VBox(verticalPadding);
        openingLayout.getChildren().addAll(addTransactions, displayTransaction);
        openingLayout.setAlignment(Pos.CENTER);

        openScreen = new Scene(openingLayout, screenWidth, screenHeight);
        primaryStage.setScene(openScreen);
        primaryStage.show();
    }

    private void toMoreInfoScene() {
        List<String> allItems = new LinkedList<>();
        for (int i = 0; i < transactionsList.size(); i++) {
            allItems.add(transactionsList.get(i).description);
        }

        Label l = new Label("Select Transaction from Dropdown for More Information");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(allItems);
        comboBox.setPromptText("choose one");
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

        detailsScene = new Scene(detailsLayout, screenWidth, screenHeight);
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

        addScene = new Scene(addLayout, screenWidth, screenHeight);
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
        Label amountLabel = new Label("Amount per Month");
        TextField amount = new TextField();
        Label tagsLabel = new Label("Identifying Tags - separate with semi-colon");
        TextField tags = new TextField();

        Button addIncome = new Button("Add");
        addIncome.setOnAction(e -> {
            try {
                double doubleAmount = Double.valueOf(amount.getText());
                CashFlowIn income = new CashFlowIn(doubleAmount);
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
        incomeLayout.getChildren().addAll(amountLabel, amount, tagsLabel, tags, addIncome);

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

        errorScene = new Scene(tryAgainLayout, screenWidth, screenHeight);
        primaryStage.setScene(errorScene);
        primaryStage.show();
    }

    private void closeProgram() {
        GSON.write(transactionsList);
        primaryStage.close();
    }

}
