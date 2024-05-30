package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class SportController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private StackPane midBox;


    @FXML
    public void initialize() {

    }




    @FXML
    public void btnSport(MouseEvent mouseEvent) {
        try {
            // Charger le fichier FXML pour la nouvelle page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/Sport/Sport2.fxml"));
            Parent root = loader.load(); // Charger la racine ici

            // Obtenir la scène actuelle
            Stage stage = (Stage) midBox.getScene().getWindow();

            // Définir la nouvelle scène sur le stage
            Scene scene = new Scene(root, 800, 600); // Utiliser la racine chargée
            stage.setTitle("Connexion");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Sport1(MouseEvent mouseEvent) {
        int sportId = 1;
        loadSportPage(sportId);
    }

    private void loadSportPage(int sportId) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/Sport/SportDetails.fxml"));
            Node sportPage = loader.load();

            SportDetailController controller = loader.getController();
            controller.loadSportDetails(sportId);
            controller.loadImageSport(sportId);

            mainPane.getChildren().setAll(sportPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Sport2(MouseEvent mouseEvent) {
        int sportId = 2;
        loadSportPage(sportId);
    }

    public void Sport3(MouseEvent mouseEvent) {
        int sportId = 3;
        loadSportPage(sportId);
    }

    public void Sport4(MouseEvent mouseEvent) {
        int sportId = 4;
        loadSportPage(sportId);
    }

    public void Sport5(MouseEvent mouseEvent) {
        int sportId = 5;
        loadSportPage(sportId);
    }
    public void Sport6(MouseEvent mouseEvent) {
        int sportId = 6;
        loadSportPage(sportId);
    }

    public void Sport7(MouseEvent mouseEvent) {
        int sportId = 7;
        loadSportPage(sportId);
    }
    public void Sport8(MouseEvent mouseEvent) {
        int sportId = 8;
        loadSportPage(sportId);
    }
    public void Sport9(MouseEvent mouseEvent) {
        int sportId = 9;
        loadSportPage(sportId);
    }
    public void Sport10(MouseEvent mouseEvent) {
        int sportId = 10;
        loadSportPage(sportId);
    }
    public void Sport11(MouseEvent mouseEvent) {
        int sportId = 11;
        loadSportPage(sportId);
    }
    public void Sport12(MouseEvent mouseEvent) {
        int sportId = 12;
        loadSportPage(sportId);
    }

    public void Sport13(MouseEvent mouseEvent) {
        int sportId = 13;
        loadSportPage(sportId);
    }

    public void Sport14(MouseEvent mouseEvent) {
        int sportId = 14;
        loadSportPage(sportId);
    }

    public void Sport15(MouseEvent mouseEvent) {
        int sportId = 15;
        loadSportPage(sportId);
    }

    public void Sport16(MouseEvent mouseEvent) {
        int sportId = 16;
        loadSportPage(sportId);
    }

    public void Sport17(MouseEvent mouseEvent) {
        int sportId = 17;
        loadSportPage(sportId);
    }

    public void Sport18(MouseEvent mouseEvent) {
        int sportId = 18;
        loadSportPage(sportId);
    }

    public void Sport19(MouseEvent mouseEvent) {
        int sportId = 19;
        loadSportPage(sportId);
    }

    public void Sport20(MouseEvent mouseEvent) {
        int sportId = 20;
        loadSportPage(sportId);
    }

    public void Sport21(MouseEvent mouseEvent) {
        int sportId = 21;
        loadSportPage(sportId);
    }

    public void Sport22(MouseEvent mouseEvent) {
        int sportId = 22;
        loadSportPage(sportId);
    }

    public void Sport23(MouseEvent mouseEvent) {
        int sportId = 23;
        loadSportPage(sportId);
    }

    public void Sport24(MouseEvent mouseEvent) {
        int sportId = 24;
        loadSportPage(sportId);
    }
}
