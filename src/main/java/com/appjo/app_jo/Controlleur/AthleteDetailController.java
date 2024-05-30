package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.kernel.pdf.PdfDocument;

import java.io.File;
import java.io.FileNotFoundException;

public class AthleteDetailController {

    @FXML private ImageView athleteImage;
    @FXML private Text athleteName;
    @FXML private ListView<String> athleteDetailsList;
    @FXML private Button closeButton;

    private Stage dialogStage;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setAthleteDetails(Athlete athlete) {
        athleteName.setText(athlete.getNom() + " " + athlete.getPrenom());

        // Adjust the image path
        String imagePath = "src/main/resources/Images/" + athlete.getId() + ".png";
        File imageFile = new File(imagePath);
        if (imageFile.exists()) {
            athleteImage.setImage(new Image(imageFile.toURI().toString()));
        } else {
            athleteImage.setImage(new Image(new File("src/main/resources/Images/avatar.png").toURI().toString()));
        }

        // Populate the ListView with details
        athleteDetailsList.getItems().addAll(
                "Pays: " + athlete.getPays(),
                "Sexe: " + athlete.getSexe(),
                "Taille: " + athlete.getTaille() + " m",
                "Poids: " + athlete.getPoids() + " kg",
                "Sport: " + athlete.getSport(),
                "Age: " + athlete.getAge(),
                "Catégorie: " + athlete.getCategorie()
        );
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    @FXML
    private void handleDownloadPdf() {
        String pdfPath = "athlete_details.pdf";
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Athlete Details"));
            document.add(new Paragraph("Name: " + athleteName.getText()));
            for (String detail : athleteDetailsList.getItems()) {
                document.add(new Paragraph(detail));
            }

            document.close();
            System.out.println("PDF created at " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
