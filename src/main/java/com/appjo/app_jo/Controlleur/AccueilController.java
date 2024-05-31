package com.appjo.app_jo.Controlleur;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AccueilController {

    public void sportPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Sport/Sport.fxml");
    }

    public void athletePage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/AthletesScene.fxml");
    }

    public void calendrierPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/CalendarView.fxml");
    }

    private void loadScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
