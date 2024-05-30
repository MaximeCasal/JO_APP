package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AthleteController {
    @FXML private TextField searchField;
    @FXML private Button searchButton;
    @FXML private Button nextButton;
    @FXML private Button prevButton;

    @FXML private ImageView athleteImage1, athleteImage2, athleteImage3, athleteImage4, athleteImage5, athleteImage6;
    @FXML private Text athleteName1, athleteName2, athleteName3, athleteName4, athleteName5, athleteName6;

    private List<Athlete> athletes = new ArrayList<>();
    private int offset = 0;
    private final int limit = 6;

    public void initialize() {
        try {
            loadAthleteData(offset, limit);
            searchButton.setOnAction(event -> filterAthletes());
            nextButton.setOnAction(event -> loadNextPage());
            prevButton.setOnAction(event -> loadPreviousPage());

            setupClickHandlers();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAthleteData(int offset, int limit) {
        athletes.clear();
        String url = "jdbc:mysql://localhost:3306/jo_app";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM athlete LIMIT ? OFFSET ?")) {
                stmt.setInt(1, limit);
                stmt.setInt(2, offset);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    athletes.add(new Athlete(
                            rs.getInt("id_athlete"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("pays"),
                            rs.getString("sexe"),
                            rs.getDouble("taille"),
                            rs.getInt("poids"),
                            rs.getString("Sport"),
                            rs.getInt("age"),
                            rs.getString("categorie")
                    ));
                }
                updateDisplay();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void filterAthletes() {
        String searchText = searchField.getText().toLowerCase();
        athletes.clear();
        String url = "jdbc:mysql://localhost:3306/jo_app";
        String user = "root";
        String password = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, password);
                 PreparedStatement stmt = conn.prepareStatement("SELECT * FROM athlete WHERE LOWER(nom) LIKE ? OR LOWER(prenom) LIKE ? LIMIT ? OFFSET ?")) {
                stmt.setString(1, "%" + searchText + "%");
                stmt.setString(2, "%" + searchText + "%");
                stmt.setInt(3, limit);
                stmt.setInt(4, offset);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    athletes.add(new Athlete(
                            rs.getInt("id_athlete"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("pays"),
                            rs.getString("sexe"),
                            rs.getDouble("taille"),
                            rs.getInt("poids"),
                            rs.getString("Sport"),
                            rs.getInt("age"),
                            rs.getString("categorie")
                    ));
                }
                updateDisplay();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadNextPage() {
        offset += limit;
        loadAthleteData(offset, limit);
    }

    private void loadPreviousPage() {
        if (offset - limit >= 0) {
            offset -= limit;
            loadAthleteData(offset, limit);
        }
    }

    private void updateDisplay() {
        ImageView[] images = {athleteImage1, athleteImage2, athleteImage3, athleteImage4, athleteImage5, athleteImage6};
        Text[] names = {athleteName1, athleteName2, athleteName3, athleteName4, athleteName5, athleteName6};

        for (int i = 0; i < limit; i++) {
            if (i < athletes.size()) {
                Athlete athlete = athletes.get(i);
                names[i].setText(athlete.getNom() + " " + athlete.getPrenom());

                String imagePath = "src/main/resources/Images/" + athlete.getId() + ".png";
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    images[i].setImage(new Image(imageFile.toURI().toString()));
                } else {
                    images[i].setImage(new Image(new File("src/main/resources/Images/avatar.png").toURI().toString()));
                }
                names[i].setOnMouseClicked(event -> showAthleteDetails(athlete));
                images[i].setOnMouseClicked(event -> showAthleteDetails(athlete));
            } else {
                names[i].setText("");
                images[i].setImage(null);
                names[i].setOnMouseClicked(null);
                images[i].setOnMouseClicked(null);
            }
        }
    }

    private void setupClickHandlers() {
        Text[] names = {athleteName1, athleteName2, athleteName3, athleteName4, athleteName5, athleteName6};
        ImageView[] images = {athleteImage1, athleteImage2, athleteImage3, athleteImage4, athleteImage5, athleteImage6};

        for (int i = 0; i < limit; i++) {
            final int index = i;
            names[i].setOnMouseClicked(event -> {
                if (index < athletes.size()) {
                    showAthleteDetails(athletes.get(index));
                }
            });
            images[i].setOnMouseClicked(event -> {
                if (index < athletes.size()) {
                    showAthleteDetails(athletes.get(index));
                }
            });
        }
    }

    private void showAthleteDetails(Athlete athlete) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/AthleteDetail.fxml"));
            VBox page = loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Athlete Details");
            dialogStage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AthleteDetailController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setAthleteDetails(athlete);

            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
