package com.appjo.app_jo.Controlleur.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import com.appjo.app_jo.Modele.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class AdminAthleteController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> athleteIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> paysColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> genreColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> tailleColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> poidsColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> sportColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> ageColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> categorieColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> prenomColumn;

    @FXML
    private TextField pays;
    @FXML
    private TextField Sexe;
    @FXML
    private TextField taille;
    @FXML
    private TextField sport;
    @FXML
    private TextField poids;
    @FXML
    private TextField age;
    @FXML
    private TextField categorie;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> athleteData = FXCollections.observableArrayList();
    private boolean isUpdateMode = false; // Variable to track update mode
    private ObservableList<String> selectedRow = null; // Variable to store the selected row

    @FXML
    private void initialize() {
        // Initialize the columns
        athleteIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        paysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        genreColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        tailleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        poidsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        ageColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        categorieColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(7)));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));
        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(9)));

        // Load data from the database
        loadAthleteDataFromDatabase();

        // Set the table data
        tableView.setItems(athleteData);
    }

    private void loadAthleteDataFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Athlete")) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("Id_athlete"));
                row.add(rs.getString("pays"));
                row.add(rs.getString("sexe"));
                row.add(rs.getString("taille"));
                row.add(rs.getString("poids"));
                row.add(rs.getString("sport_id")); // Change to "sport_id"
                row.add(rs.getString("age"));
                row.add(rs.getString("categorie"));
                row.add(rs.getString("nom"));
                row.add(rs.getString("prenom"));
                athleteData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBtn(MouseEvent mouseEvent) {
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() ||
                age.getText().isEmpty() || pays.getText().isEmpty() ||
                categorie.getText().isEmpty() || sport.getText().isEmpty() || Sexe.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            int sportId = getSportId(sport.getText());
            if (sportId == 0) {
                wrongLabel.setText("Sport non trouvé dans la base de données.");
                return;
            }

            int lastAthleteId = getLastAthleteIdFromDatabase();

            ObservableList<String> row = FXCollections.observableArrayList(
                    String.valueOf(lastAthleteId + 1),
                    pays.getText(),
                    Sexe.getText(),
                    taille.getText(),
                    poids.getText(),
                    String.valueOf(sportId),
                    age.getText(),
                    categorie.getText(),
                    nom.getText(),
                    prenom.getText()
            );
            athleteData.add(row);

            clearTextFields();

            addAthleteToDatabase(row);
        }
    }

    private int getSportId(String sportName) {
        String query = "SELECT id FROM Sport WHERE nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sportName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if the sport is not found
    }

    private int getLastAthleteIdFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(Id_athlete) FROM Athlete")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if there are no athletes in the database
    }

    private void addAthleteToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Athlete (Id_athlete, pays, sexe, taille, poids, sport_id, age, categorie, nom, prenom) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            stmt.setString(5, row.get(4));
            stmt.setInt(6, Integer.parseInt(row.get(5))); // Assuming row.get(5) contains the sport_id
            stmt.setString(7, row.get(6));
            stmt.setString(8, row.get(7));
            stmt.setString(9, row.get(8));
            stmt.setString(10, row.get(9));

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
                pays.setText(selectedRow.get(1));
                Sexe.setText(selectedRow.get(2));
                taille.setText(selectedRow.get(3));
                poids.setText(selectedRow.get(4));
                sport.setText(selectedRow.get(5));
                age.setText(selectedRow.get(6));
                categorie.setText(selectedRow.get(7));
                nom.setText(selectedRow.get(8));
                prenom.setText(selectedRow.get(9));
                isUpdateMode = true;
            }
        } else {
            // Second click: update the selected row in the database
            if (selectedRow != null) {
                selectedRow.set(1, pays.getText());
                selectedRow.set(2, Sexe.getText());
                selectedRow.set(3, taille.getText());
                selectedRow.set(4, poids.getText());
                selectedRow.set(5, sport.getText());
                selectedRow.set(6, age.getText());
                selectedRow.set(7, categorie.getText());
                selectedRow.set(8, nom.getText());
                selectedRow.set(9, prenom.getText());

                updateAthleteInDatabase(selectedRow.get(0), pays.getText(), Sexe.getText(), taille.getText(), poids.getText(), sport.getText(), age.getText(), categorie.getText(), nom.getText(), prenom.getText());

                tableView.refresh();

                clearTextFields();
                isUpdateMode = false;
                selectedRow = null;
            }
        }
    }

    private void updateAthleteInDatabase(String athleteId, String paysValue, String sexeValue, String tailleValue, String poidsValue, String sportValue, String ageValue, String categorieValue, String nomValue, String prenomValue) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Athlete SET pays=?, sexe=?, taille=?, poids=?, sport_id=?, age=?, categorie=?, nom=?, prenom=? WHERE Id_athlete=?")) {

            stmt.setString(1, paysValue);
            stmt.setString(2, sexeValue);
            stmt.setString(3, tailleValue);
            stmt.setString(4, poidsValue);
            stmt.setInt(5, getSportId(sportValue));
            stmt.setString(6, ageValue);
            stmt.setString(7, categorieValue);
            stmt.setString(8, nomValue);
            stmt.setString(9, prenomValue);
            stmt.setString(10, athleteId);

            stmt.executeUpdate();

            // Effacer les champs de texte après la mise à jour
            clearTextFields();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            athleteData.remove(selectedRow);
            deleteAthleteFromDatabase(selectedRow.get(0));
        }
    }

    private void deleteAthleteFromDatabase(String athleteId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Athlete WHERE Id_athlete = ?")) {

            stmt.setInt(1, Integer.parseInt(athleteId));
            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clearBtn(MouseEvent mouseEvent) {
        clearTextFields();
        wrongLabel.setText("");
        isUpdateMode = false; // Reset update mode when clearing the fields
    }

    public void saveBtn(MouseEvent mouseEvent) {
        // Implementation of save button
    }

    private void clearTextFields() {
        // Effacer les champs de texte après la mise à jour
        pays.clear();
        Sexe.clear();
        taille.clear();
        poids.clear();
        sport.clear();
        age.clear();
        categorie.clear();
        nom.clear();
        prenom.clear();
    }

}
