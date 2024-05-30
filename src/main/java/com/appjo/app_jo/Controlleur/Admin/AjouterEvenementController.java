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
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterEvenementController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> sportIdColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> eventColumn;
    @FXML
    private TextField sport_id;
    @FXML
    private TextField event;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> eventData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize the columns
        sportIdColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        eventColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));

        // Set the table data
        tableView.setItems(eventData);
    }

    @FXML
    private void addBtn() {
        if (sport_id.getText().isEmpty() || event.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            ObservableList<String> row = FXCollections.observableArrayList(
                    sport_id.getText(),
                    event.getText()
            );

            eventData.add(row);

            // Clear text fields after adding
            sport_id.clear();
            event.clear();
            wrongLabel.setText(""); // Clear the error message if any
        }
    }

    @FXML
    private void supprBtn() {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        eventData.remove(selectedRow);
    }

    @FXML
    private void clearBtn() {
        sport_id.clear();
        event.clear();
        wrongLabel.setText("");
    }

    @FXML
    private void saveBtn() {
        if (eventData.isEmpty()) {
            wrongLabel.setText("Il n'y a rien à sauvegarder");
        } else {
            SharedData.setAthleteData(eventData);
            wrongLabel.setText("Données sauvegardées avec succès");
            // Optionally, switch to the next page here if needed
        }
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

    public void pageResultatAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AdminResultat.fxml");
    }

}
