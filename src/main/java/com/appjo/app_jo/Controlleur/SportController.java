package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class SportController {

    @FXML
    private BorderPane mainPane;
    @FXML
    private StackPane midBox;
    @FXML
    private ImageView backgroundImage;

    @FXML
    public void initialize() {
        // Charger l'image
        String imagePath = "/Images/Sites_Jeux.jpeg";
        Image image = new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toExternalForm());
        backgroundImage.setImage(image);

        // Mettre à jour le clip personnalisé quand la taille du StackPane change
        midBox.layoutBoundsProperty().addListener((obs, oldVal, newVal) -> updateClip());
    }

    private void updateClip() {
        double width = midBox.getWidth();
        double height = midBox.getHeight();

        if (width > 0 && height > 0) {
            Path clip = new Path();
            clip.getElements().add(new MoveTo(0, 0));
            clip.getElements().add(new LineTo(width - 200, 0));
            clip.getElements().add(new ArcTo(200, 100, 0, width, 100, false, true));
            clip.getElements().add(new LineTo(width, height - 100));
            clip.getElements().add(new ArcTo(200, 100, 0, width - 200, height, false, true));
            clip.getElements().add(new LineTo(0, height));
            clip.getElements().add(new ClosePath());

            backgroundImage.setClip(clip);
        }
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
}
