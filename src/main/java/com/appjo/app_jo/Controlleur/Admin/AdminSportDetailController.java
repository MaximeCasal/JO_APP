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
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSportDetailController {

    private AjouterAthleteController ajouterAthleteController;

    public void setAjouterAthleteController(AjouterAthleteController ajouterAthleteController) {
        this.ajouterAthleteController = ajouterAthleteController;
    }

    // Utilisez cette méthode pour obtenir la taille de athleteData de AjouterAthleteController
    private int getNumberOfAthletes() {
        if (ajouterAthleteController != null) {
            return ajouterAthleteController.getNumberOfAthletes();
        } else {
            return 0; // ou une autre valeur par défaut selon votre logique
        }
    }

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> sportIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nbrAthleteDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> lieuColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> eventColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> athleteParticipantColumn;

    @FXML
    private TextField sport_id;
    @FXML
    private TextField nom;
    @FXML
    private TextField lieu;

    @FXML
    private Label wrongLabel;

    @FXML
    public void initialize() {
        sportIDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(0)));
        nomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(1)));
        nbrAthleteDColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(2)));
        lieuColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(3)));
        eventColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(4)));
        athleteParticipantColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(5)));

        updateTableWithDataFromSharedData();
    }

    @FXML
    public void addBtn() {
        if (sport_id.getText().isEmpty() || nom.getText().isEmpty() || lieu.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
            return;
        }

        if (ajouterAthleteController != null) {
            int numberOfAthletes = ajouterAthleteController.getNumberOfAthletes();
            ObservableList<String> newSportData = FXCollections.observableArrayList();
            newSportData.add(sport_id.getText());
            newSportData.add(nom.getText());
            newSportData.add(String.valueOf(numberOfAthletes)); // Utilisez le nombre d'athlètes de AjouterAthleteController
            newSportData.add(lieu.getText());
            newSportData.add(""); // Les événements seront mis à jour lors de la mise à jour du tableau
            newSportData.add(""); // Les athlètes participants seront mis à jour lors de la mise à jour du tableau

            SharedData.addSportData(newSportData);

            updateTableWithDataFromSharedData();

            sport_id.clear();
            nom.clear();
            lieu.clear();
            wrongLabel.setText("");
        } else {
            // Gérer le cas où ajouterAthleteController est null
        }
    }

    @FXML
    public void updateBtn() {
        // Implémentation de la mise à jour
    }

    @FXML
    public void supprBtn() {
        // Implémentation de la suppression
    }

    @FXML
    public void clearBtn() {
        sport_id.clear();
        nom.clear();
        lieu.clear();
        wrongLabel.setText("");
    }

    @FXML
    public void addEvent() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/Admin/AjouterEvenement.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addAthlete() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/Admin/AjouterAthlete.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveBtn() {
        // Implémentation de la sauvegarde
    }

    private void updateTableWithDataFromSharedData() {
        tableView.setItems(SharedData.getSportData());
    }
}
