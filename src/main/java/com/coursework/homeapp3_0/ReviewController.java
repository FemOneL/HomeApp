package com.coursework.homeapp3_0;

import com.coursework.homeapp3_0.database.Appliance;
import com.coursework.homeapp3_0.database.DatabaseHandler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class ReviewController {
    private Parent root;
    File file;
    List<Appliance> appliances = new ArrayList<>();
    Appliance currentAppliance;
    ToggleGroup group = new ToggleGroup();

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private ImageView imageView;
    @FXML
    private Button onButton;
    @FXML
    private Label infoLabel;
    @FXML
    private RadioButton radioShowAll;
    @FXML
    private RadioButton radioShowInRange;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField range1;
    @FXML
    private TextField range2;
    @FXML
    private ListView<Appliance> listView;

    @FXML
    void switchToMenu(ActionEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void removeAppliance(ActionEvent event){
        DatabaseHandler databaseHandler = new DatabaseHandler();
        if (listView.getSelectionModel().isEmpty())
            return;
        try {

            databaseHandler.removeFromDb(currentAppliance);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        appliances.remove(currentAppliance);
        infoLabel.setText("");
        file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/appliances.png");
        onButton.setStyle("-fx-background-color: yellow");
        imageView.setImage(new Image(file.toURI().toString()));
        listView.getItems().clear();
        listView.getItems().addAll(appliances);
    }

    @FXML
    void plugInAppliance(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        if (currentAppliance == null)
            return;
        if (currentAppliance.getStatus().equals("on")){
            onButton.setStyle("-fx-background-color: red");
            currentAppliance.setStatus("off");
            databaseHandler.changeInDb(currentAppliance);
        } else {
            onButton.setStyle("-fx-background-color: green");
            currentAppliance.setStatus("on");
            databaseHandler.changeInDb(currentAppliance);
        }
        infoLabel.setText(currentAppliance.showCharacteristic());
    }

    @FXML
    void showInRange(ActionEvent event){
        int fRange = 0, sRange = 0;
        radioShowAll.setToggleGroup(group);
        radioShowInRange.setToggleGroup(group);
        if (radioShowAll.isSelected()){
            listView.getItems().clear();
            listView.getItems().addAll(appliances);
        } else{
            if (!range1.getText().trim().isEmpty() && !range2.getText().trim().isEmpty()){
                try {
                    fRange = Integer.parseInt(range1.getText());
                    sRange = Integer.parseInt(range2.getText());
                }catch (NumberFormatException e){
                    errorLabel.setText("Please, enter integer number of range");
                    infoLabel.setText("");
                    onButton.setStyle("-fx-background-color: yellow");
                    file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/appliances.png");
                    imageView.setImage(new Image(file.toURI().toString()));
                    listView.getItems().clear();
                    radioShowAll.fire();
                    return;
                }
            }else {
                errorLabel.setText("Please, enter any number of range");
                infoLabel.setText("");
                onButton.setStyle("-fx-background-color: yellow");
                file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/appliances.png");
                imageView.setImage(new Image(file.toURI().toString()));
                listView.getItems().clear();
                radioShowAll.fire();
                return;
            }
            listView.getItems().clear();
            for (Appliance app : appliances){
                if (app.getPower() >= fRange && app.getPower() <= sRange) {
                    listView.getItems().add(app);
                }
            }
        }
    }

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getFromDb();
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
        radioShowAll.fire();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Appliance>() {
            @Override
            public void changed(ObservableValue<? extends Appliance> observableValue, Appliance appliance, Appliance t1) {
                currentAppliance = listView.getSelectionModel().getSelectedItem();
                if (currentAppliance != null) {
                    infoLabel.setText(currentAppliance.showCharacteristic());
                    if (currentAppliance.getName().equalsIgnoreCase("microwave"))
                        file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/microwave.png");
                    else if (currentAppliance.getName().equalsIgnoreCase("refrigerator"))
                        file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/refrigerator.png");
                    else if(currentAppliance.getName().equalsIgnoreCase("washing machine"))
                        file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/washing machine.png");
                    else
                        file = new File("src/main/resources/com/coursework/homeapp3_0/pictures/appliances.png");
                    Image image = new Image(file.toURI().toString());
                    imageView.setImage(image);
                    if (currentAppliance.getStatus().equals("off"))
                        onButton.setStyle("-fx-background-color: red");
                    else
                        onButton.setStyle("-fx-background-color: green");
                }
            }
        });
    }
}
