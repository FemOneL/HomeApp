package com.coursework.homeapp3_0;

import com.coursework.homeapp3_0.database.Appliance;
import com.coursework.homeapp3_0.database.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MenuController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    List<Appliance> appliances = new ArrayList<>();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private TextField timeText;
    @FXML
    private Label powerConsLabel;
    @FXML
    private Label errorLabel;

    public void switchToAddWindow(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddApplianceWindow.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void switchToReviewWindow(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReviewWindow.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void startCalculation(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getAllPluggedFromDb();
        errorLabel.setText("");
        String output;
        int iter = 0;
        while (resultSet.next()) {
            appliances.add(new Appliance());
            appliances.get(iter).setName(resultSet.getString(2));
            appliances.get(iter).setModel(resultSet.getString(3));
            appliances.get(iter).setCompany(resultSet.getString(4));
            appliances.get(iter).setPower(resultSet.getInt(5));
            appliances.get(iter).setStatus(resultSet.getString(6));
            iter++;
        }
        output = String.format("%.1f kW/h", doCalculation());
        powerConsLabel.setText(output);

    }

    public double doCalculation(){
        double result = 0.0;
        double hour = 0.0;
        if (appliances.isEmpty()) {
            errorLabel.setText("Please, turn on at least one device");
            return result;
        }else if (timeText.getText().trim().equals("")) {
                return result;
        }
        else{
            try {
                hour = Double.parseDouble(timeText.getText());
                if (hour < 0) {
                    errorLabel.setText("Please, enter positive number");
                    return result;
                }
            } catch (NumberFormatException e) {
                errorLabel.setText("Please, enter real or integer number");
                return result;
            }
        }
        for (Appliance app : appliances){
            result += ((double)app.getPower()/1000) * hour;
        }
        return result;
    }
}