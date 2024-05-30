package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CalendarController {

    @FXML
    private Button btn24;
    @FXML
    private Button btn25;
    @FXML
    private Button btn26;
    @FXML
    private Button btn27;
    @FXML
    private Button btn28;
    @FXML
    private Button btn29;
    @FXML
    private Button btn30;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;

    @FXML
    public void initialize() {
        btn24.setOnAction(event -> openNewPage("/com/appjo/app_jo/jour24.fxml"));

        btn25.setOnAction(event -> openNewPage("CalendarView.fxml"));
        btn26.setOnAction(event -> openNewPage("/com/appjo/app_jo/jour24.fxml"));
        btn27.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn28.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn29.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn30.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn1.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn2.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn3.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn4.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn5.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn6.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn7.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn8.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn9.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn10.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
        btn11.setOnAction(event -> openNewPage("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
    }

    private void openNewPage(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            AnchorPane newPage = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(newPage));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
