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

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.appjo.app_jo.Modele.DatabaseConnection;

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

    @FXML
    private void initialize() {
        EventIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        descrColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        athleteColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));

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

    public void addBtn(MouseEvent mouseEvent) {
        if (nom.getText().isEmpty() || description.getText().isEmpty() ||
                athParticipant.getText().isEmpty() || sport.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            try {
                int lastEventId = getLastEventIdFromDatabase();

                ObservableList<String> row = FXCollections.observableArrayList(
                        String.valueOf(lastEventId + 1),
                        nom.getText(),
                        description.getText(),
                        sport.getText(),
                        athParticipant.getText()
                );
                eventData.add(row);

                clearTextFields();

                addEventToDatabase(row);
            } catch (NumberFormatException e) {
                wrongLabel.setText("ID de sport doit être un nombre");
            }
        }
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

    private void addEventToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Evenement (id_event, nom, description, id_sport, id_athlete) VALUES (?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            stmt.setString(5, row.get(4));

            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            eventData.remove(selectedIndex);

            selectedRow.set(1, nom.getText());
            selectedRow.set(2, description.getText());
            selectedRow.set(3, sport.getText());
            selectedRow.set(4, athParticipant.getText());

            eventData.add(selectedIndex, selectedRow);
            updateEventInDatabase(selectedRow);
        } else {
            wrongLabel.setText("Aucun événement sélectionné pour la mise à jour");
        }
    }

    private void updateEventInDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Evenement SET nom=?, description=?, id_sport=?, id_athlete=? WHERE id_event=?")) {

            stmt.setString(1, row.get(1));
            stmt.setString(2, row.get(2));
            stmt.setString(3, row.get(3));
            stmt.setString(4, row.get(4));
            stmt.setInt(5, Integer.parseInt(row.get(0)));

            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedItem = tableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            tableView.getItems().remove(selectedIndex);
            deleteEventFromDatabase(selectedItem.get(0));
            clearTextFields();
        } else {
            wrongLabel.setText("Aucun événement sélectionné pour la suppression");
        }
    }

    private void deleteEventFromDatabase(String event_id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Evenement WHERE id_event=?")) {

            stmt.setString(1, event_id);

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
        wrongLabel.setText("");
       // isUpdateMode = false;
    }
}
