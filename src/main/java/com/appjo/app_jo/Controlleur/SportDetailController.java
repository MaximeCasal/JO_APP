package com.appjo.app_jo.Controlleur;

import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Base64;

public class SportDetailController {

    @FXML
    private Text nomSport;
    @FXML
    private Text text1;
    @FXML
    private Text tire1;
    @FXML
    private Text text2;
    @FXML
    private Text titre2;
    @FXML
    private Text text3;
    @FXML
    private TextField barreRecherche;
    @FXML
    private ImageView imgHori;  // Assurez-vous que cela correspond à fx:id dans votre FXML
    @FXML
    private ImageView imgVerti; // Assurez-vous que cela correspond à fx:id dans votre FXML
    @FXML
    private Label wrongLabel;

    @FXML
    public void initialize() {
        assert imgHori != null : "fx:id=\"imgHori\" was not injected: check your FXML file 'SportDetail.fxml'.";
        assert imgVerti != null : "fx:id=\"imgVerti\" was not injected: check your FXML file 'SportDetail.fxml'.";
        System.out.println("imgHori and imgVerti have been initialized");
    }
    public static String convertBlobToBase64(Blob blob) throws SQLException {
        byte[] blobBytes = blob.getBytes(1, (int) blob.length());
        return Base64.getEncoder().encodeToString(blobBytes);
    }


    public void loadImageSport(int sportId) {
        String query = "SELECT imageHori, imageVerti FROM Sport WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sportId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                // Récupérer les blobs
                Blob blobHori = resultSet.getBlob("imageHori");
                byte[] imageBytesHori = blobHori.getBytes(1, (int) blobHori.length());
                Blob blobVerti = resultSet.getBlob("imageVerti");
                byte[] imageBytesVerti = blobVerti.getBytes(1, (int) blobVerti.length());

                // Convertir les blobs en Base64
                String base64ImageHori = java.util.Base64.getEncoder().encodeToString(imageBytesHori);
                String base64ImageVerti = java.util.Base64.getEncoder().encodeToString(imageBytesVerti);

                Image ImageHori = new Image(new ByteArrayInputStream(java.util.Base64.getDecoder().decode(base64ImageHori)));
                Image ImageVerti = new Image(new ByteArrayInputStream(java.util.Base64.getDecoder().decode(base64ImageVerti)));


                // Définir les images sur les ImageView
                this.imgHori.setImage(ImageHori);
                this.imgVerti.setImage(ImageVerti);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public void loadSportDetails(int sportId) {
        String query = "SELECT nom, text1, tire1, text2, titre2, text3 FROM Sport WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sportId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomSport.setText(resultSet.getString("nom"));
                text1.setText(resultSet.getString("text1"));
                tire1.setText(resultSet.getString("tire1"));
                text2.setText(resultSet.getString("text2"));
                titre2.setText(resultSet.getString("titre2"));
                text3.setText(resultSet.getString("text3"));
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void sportPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Sport/Sport.fxml");
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

    public void searchIn(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            String searchTerm = barreRecherche.getText();
            boolean resultFound = BarreRechercheController.rechercherEtAfficherResultats(searchTerm);

            if (resultFound) {
                wrongLabel.setText("Résultat trouvé");
            } else {
                wrongLabel.setText("Aucun résultat trouvé");
            }
        }
    }

    public void evenementPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/EventDetail.fxml");
    }

    public void calendrierPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/CalendarView.fxml");
    }

    public void accueilPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/PrimaryScene.fxml");
    }

    public void athletePage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Sport/AthletesScene.fxml");
    }
}
