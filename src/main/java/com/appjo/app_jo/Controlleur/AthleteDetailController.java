package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AthleteDetailController {

    @FXML private ImageView athleteImage;
    @FXML private Text athleteName;
    @FXML private Text athleteDetails;
    @FXML private Button closeButton;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAthleteDetails(Athlete athlete) {
        athleteName.setText(athlete.getNom() + " " + athlete.getPrenom());
        athleteDetails.setText("Detailed information about " + athlete.getNom() + " " + athlete.getPrenom());
        //athleteImage.setImage(new Image("path/to/athlete/image" + athlete.getId() + ".png")); // Adjust the path as needed
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }
}

