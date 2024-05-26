package com.appjo.app_jo.Controlleur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CanlendrierController {

    @FXML
    private TableView<String> tableView;

    @FXML
    private void initialize() {
        // Créer une liste observable avec 10 lignes
        ObservableList<String> data = FXCollections.observableArrayList(
                "Row 1", "Row 2", "Row 3", "Row 4", "Row 5",
                "Row 6", "Row 7", "Row 8", "Row 9", "Row 10"
        );

        // Assigner la liste observable au TableView
        tableView.setItems(data);
    }

    @FXML
    private void ajouterLigne() {
        // Obtenez la liste observable associée au TableView
        ObservableList<String> data = tableView.getItems();

        // Ajoutez une nouvelle ligne à la liste observable
        data.add("Nouvelle ligne");

        // Rafraîchissez le TableView pour afficher les changements
        tableView.refresh();
    }


}
