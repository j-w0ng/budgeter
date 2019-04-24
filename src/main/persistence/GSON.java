package main.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import main.Purchases;
import main.Transactions;
import main.cashflow.CashFlowIn;
import main.cashflow.RecurringCashFlowOut;
import main.goals.SavingGoals;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class GSON {

    public static final File jsonDataFile = new File("./resources/json/transactions.json");

    public static List<Transactions> read() {
        Gson gson = new Gson();
        try {
            List<String> transactionListOfString = Files.readAllLines(jsonDataFile.toPath());
            if (transactionListOfString.isEmpty()) {
                return null;
            }
            StringBuilder inputString = new StringBuilder();
            for (String line: transactionListOfString) {
                inputString.append(line);
            }
            return parseListOfTransactions(inputString.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void write(List<Transactions> t) {
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        String infoToSave = gson.toJson(t);
        try {
            FileWriter file = new FileWriter(jsonDataFile);
            file.write(infoToSave);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<Transactions> parseListOfTransactions(String input) {
        List<Transactions> transactionsList = new LinkedList<>();
        JSONArray transactionArray = new JSONArray(input);
        Gson gson = new Gson();
        for (Object object : transactionArray) {
            JSONObject t = (JSONObject) object;
            switch (t.get("status").toString().toLowerCase()) {
                case "income":
                    transactionsList.add(gson.fromJson(String.valueOf(object), CashFlowIn.class));
                    break;
                case "recurring_bills":
                    transactionsList.add(gson.fromJson(String.valueOf(object), RecurringCashFlowOut.class));
                    break;
                case "creditcard_bill":
                    transactionsList.add(gson.fromJson(String.valueOf(object), Purchases.class));
                    break;
                case "to_purchase:":
                    transactionsList.add(gson.fromJson(String.valueOf(object), SavingGoals.class));
                    break;
                case "purchased":
                    transactionsList.add(gson.fromJson(String.valueOf(object), SavingGoals.class));
                    break;
            }
        }
        return transactionsList;
    }
}
