package com.appjo.app_jo.Controlleur.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.stage.Stage;

public class AdminEvenementController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> EventIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> descrColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> sportColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> athleteColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> athleteIDColumn;

    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField sport;
    @FXML
    private TextField athParticipant;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> eventData = FXCollections.observableArrayList();
    private boolean isUpdateMode = false; // Variable to track update mode
    private ObservableList<String> selectedRow = null; // Variable to store the selected row

    @FXML
    private void initialize() {
        EventIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        descrColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        athleteColumn.setCellValueFactory(data -> new SimpleStringProperty(getAthleteNameFromDatabase(data.getValue().get(4)))); // Getting athlete name
        athleteIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4))); // Displaying athlete ID

        loadEventDataFromDatabase();
        tableView.setItems(eventData);
    }

    private void loadEventDataFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Evenement")) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList(
                            rs.getString("id_event"),
                            rs.getString("nom"),
                            rs.getString("description"),
                            rs.getString("id_sport"),
                            rs.getString("id_athlete")
                    );
                    eventData.add(row);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private String getAthleteNameFromDatabase(String athleteId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT nom FROM Athlete WHERE Id_athlete = ?")) {

            stmt.setString(1, athleteId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("nom");
                } else {
                    return ""; // Return empty string if athlete name not found
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public void addBtn(MouseEvent mouseEvent) {
        if (nom.getText().isEmpty() || description.getText().isEmpty() ||
                athParticipant.getText().isEmpty() || sport.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            try {
                int lastEventId = getLastEventIdFromDatabase();

                // Récupérer le nom de l'athlète
                int sportId = getSportIdFromDatabase(sport.getText());
                int athleteId = getAthleteIdFromDatabase(athParticipant.getText());

                if(sportId != 0 && athleteId != 0) {
                    // Insérer le nom de l'athlète dans la colonne id_athlete
                    ObservableList<String> row = FXCollections.observableArrayList(
                            String.valueOf(lastEventId + 1),
                            nom.getText(),
                            description.getText(),
                            String.valueOf(sportId),
                            athParticipant.getText(),
                            String.valueOf(athleteId)
                    );
                    eventData.add(row);
                    clearTextFields();
                    addEventToDatabase(row);
                }

                if(athleteId == 0) {
                    wrongLabel.setText("L'athlete n'existe pas !");
                }
                if(sportId == 0) {
                    wrongLabel.setText("Le sport n'existe pas !");
                }


            } catch (NumberFormatException e) {
                wrongLabel.setText("ID de sport doit être un nombre");
            }
        }
    }

    private int getSportIdFromDatabase(String sportName) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT id FROM Sport WHERE nom = ?")) {

            stmt.setString(1, sportName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return 0; // Retourne 0 si le nom de sport n'est pas trouvé
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Retourne 0 en cas d'erreur ou de problème de connexion à la base de données
    }

    private int getLastEventIdFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MAX(id_event) FROM Evenement")) {

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getAthleteIdFromDatabase(String athleteName) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT Id_athlete FROM Athlete WHERE nom = ?")) {

            stmt.setString(1, athleteName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Id_athlete");
                } else {
                    return 0; // Retourne 0 si l'athlète n'est pas trouvé
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Retourne 0 en cas d'erreur ou de problème de connexion à la base de données
    }

    private void addEventToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Evenement (id_event, nom, description, id_sport, id_athlete) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            int athleteId = getAthleteIdFromDatabase(row.get(4));
            stmt.setInt(5, athleteId);

            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    public void updateBtn(MouseEvent mouseEvent) {
        if (!isUpdateMode) {
            // First click: fill the text fields with selected row data
            selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                nom.setText(selectedRow.get(1));
                description.setText(selectedRow.get(2));
                sport.setText(selectedRow.get(3));
                athParticipant.setText(selectedRow.get(4));
                isUpdateMode = true;
            }
        } else {
            // Second click: update the selected row in the database
            if ((selectedRow != null)) {
                int sportId = getSportIdFromDatabase(sport.getText());
                int athleteId = getAthleteIdFromDatabase(athParticipant.getText());
                selectedRow.set(1, nom.getText());
                selectedRow.set(2, description.getText());
                selectedRow.set(3, String.valueOf(sportId));
                selectedRow.set(4, athParticipant.getText());

                updateEventInDatabase(selectedRow);

                clearTextFields();
                isUpdateMode = false;
            }
        }
    }

    private void updateEventInDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Evenement SET nom=?, description=?, id_sport=?, id_athlete=? WHERE id_event=?")) {

            stmt.setString(1, row.get(1));
            stmt.setString(2, row.get(2));
            stmt.setString(3, row.get(3));

            // Vérifier si l'athlète existe dans la base de données
            int athleteId = getAthleteIdFromDatabase(row.get(4));
            if (athleteId != 0) {
                // Si l'athlète existe, insérer son ID
                stmt.setInt(4, athleteId);
            } else {
                // Sinon, insérer '0' dans la colonne id_athlete
                stmt.setInt(4, 0);
            }

            stmt.setInt(5, Integer.parseInt(row.get(0)));

            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            eventData.remove(selectedRow);
            deleteEventFromDatabase(selectedRow);
        }
    }

    private void deleteEventFromDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Evenement WHERE id_event=?")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    private void clearTextFields() {
        nom.clear();
        description.clear();
        sport.clear();
        athParticipant.clear();
        wrongLabel.setText("");
    }

    public void clearBtn(MouseEvent mouseEvent) {
        clearTextFields();
    }

    public void deconnexion(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Connection.fxml");
    }

    public void pageEventAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AdminEvent.fxml");
    }

    public void pageSportAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AdminSportDetails.fxml");
    }

    public void pageAthelteAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AthleteAdmin.fxml");
    }

    public void pageUtilisateurAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AdminUtilisateur.fxml");
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
            wrongLabel.setText("Erreur lors du chargement de la page.");
        }
    }
}
