package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.persistence.GSON;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;

public class Main extends Application {

    private static List<Transactions> transactionsList;
    private Stage primaryStage;
    private Scene openScreen, addScene, moreInfoScene, ccStatementScene, errorScene, detailsScene;

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

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(allItems);

        Button select = new Button("Select");
        select.setOnAction(e -> toDetailsScene(choiceBox.getValue()));

        VBox moreInfoLayout = new VBox(verticalPadding);
        moreInfoLayout.getChildren().addAll(l, choiceBox, select);
        moreInfoLayout.setAlignment(Pos.CENTER);

        moreInfoScene = new Scene(moreInfoLayout, screenWidth, screenHeight);
        primaryStage.setScene(moreInfoScene);
        primaryStage.show();
    }

    private void toDetailsScene(String s) {
        Transactions moreInfo = null;
        for (Transactions t: transactionsList) {
            if (t.getDescription().equals(s)) {
                moreInfo = t;
            }
        }

        Label l = new Label (moreInfo.toString());

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
        addIncomeSource.setOnAction(e -> System.out.println("Income"));
        addRecurringBills = new Button("Recurring Bills");
        addRecurringBills.setOnAction(e -> System.out.println("Recurring Bill"));
        addSavingGoal = new Button("Saving Goal");
        addSavingGoal.setOnAction(e -> System.out.println("Savings"));

        VBox addLayout = new VBox(verticalPadding);
        addLayout.setAlignment(Pos.CENTER);
        addLayout.getChildren().addAll(addFromCCStatement, addIncomeSource, addRecurringBills, addSavingGoal);

        addScene = new Scene(addLayout, screenWidth, screenHeight);
        primaryStage.setScene(addScene);
        primaryStage.show();

    }

    private void toCCStatementScene() {
        TextField description = new TextField();
        TextField location = new TextField();

        Button addCCStatement = new Button("Add");
        addCCStatement.setOnAction(e -> {
            try {
                Purchases p = new Purchases(location.getText(), description.getText());
                transactionsList.add(p);
                primaryStage.setScene(openScreen);
            } catch (FileNotFoundException ex) {
                errorScene();
            }
        });

        VBox ccStatementLayout = new VBox(verticalPadding);
        ccStatementLayout.setAlignment(Pos.CENTER);
        ccStatementLayout.getChildren().addAll(description, location, addCCStatement);

        ccStatementScene = new Scene(ccStatementLayout, screenWidth, screenHeight);
        primaryStage.setScene(ccStatementScene);
        primaryStage.show();

    }

    private void errorScene() {
        Label l = new Label("Sorry, could not find file. \n " +
                "Please try again");
        Button tryAgain = new Button("Try Again");
        tryAgain.setOnAction(d -> toCCStatementScene());

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
