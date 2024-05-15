package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GestionNavigation {

    public static void chargerScene(String fxmlPath, Stage stage) throws Exception {
        Parent root = FXMLLoader.load(GestionNavigation.class.getResource(fxmlPath));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // Autres méthodes de navigation...
}
