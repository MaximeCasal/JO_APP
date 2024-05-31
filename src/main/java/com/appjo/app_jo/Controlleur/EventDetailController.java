package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class EventDetailController {

    @FXML
    private Label eventNameLabel;

    @FXML
    private Label eventDateLabel;

    @FXML
    private Label eventTimeLabel;

    @FXML
    private Label eventDescriptionLabel;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEventDetails(Event event) {
        eventNameLabel.setText(event.getName());
        eventDateLabel.setText(event.getDate().toString());
        eventTimeLabel.setText(event.getTime().toString());
        eventDescriptionLabel.setText(event.getDescription());
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }
}



