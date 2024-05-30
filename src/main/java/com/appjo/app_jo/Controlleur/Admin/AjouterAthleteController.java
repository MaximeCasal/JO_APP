package com.appjo.app_jo.Controlleur.Admin;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AjouterAthleteController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> prenomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> paysColumn;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField pays;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> athleteData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize the columns
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        paysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));

        // Set the table data
        tableView.setItems(athleteData);
    }

    @FXML
    private void addBtn() {
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || pays.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            ObservableList<String> row = FXCollections.observableArrayList(
                    nom.getText(),
                    prenom.getText(),
                    pays.getText()
            );

            athleteData.add(row);

            // Clear text fields after adding
            nom.clear();
            prenom.clear();
            pays.clear();
            wrongLabel.setText(""); // Clear the error message if any
        }
    }

    @FXML
    private void supprBtn() {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        athleteData.remove(selectedRow);
    }

    @FXML
    private void clearBtn() {
        nom.clear();
        prenom.clear();
        pays.clear();
        wrongLabel.setText("");
    }

    @FXML
    private void saveBtn() {
        if (athleteData.isEmpty()) {
            wrongLabel.setText("Il n'y a rien à sauvegarder");
        } else {
            SharedData.setAthleteData(athleteData);
            int athleteTaille = athleteData.size();
            wrongLabel.setText("Données sauvegardées avec succès");
            // Optionally, switch to the next page here if needed
        }
    }




}
