package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Info_event_Controller implements Initializable {
    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox contentVBox; // Le conteneur du texte à animer

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Ajoute un écouteur d'événements de défilement à la barre de défilement de la zone de texte
        scrollPane.vvalueProperty().addListener((observable, oldValue, newValue) -> {
            // Calculer le pourcentage de défilement
            double scrollPercentage = newValue.doubleValue();

            // Calculer l'opacité en fonction de la position de défilement
            double opacity = Math.min(1, Math.max(0, scrollPercentage * 2)); // Opacité de 0 à 1 entre 0% et 50% de défilement

            // Appliquer l'opacité au conteneur du texte
            contentVBox.setOpacity(opacity);

            // Si le défilement est arrivé en bas de la page, affichez tout le contenu
            if (scrollPercentage == 1.0) {
                contentVBox.setOpacity(1.0);
            }
        });
    }
}


