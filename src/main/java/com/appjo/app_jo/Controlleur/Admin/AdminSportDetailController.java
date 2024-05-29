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

public class AdminSportDetailController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> sportIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> descrColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> titre1Column;
    @FXML
    private TableColumn<ObservableList<String>, String> resumColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> titre2Column;
    @FXML
    private TableColumn<ObservableList<String>, String> histoireOlympColumn;

    @FXML
    private TextField nom;
    @FXML
    private TextField description;
    @FXML
    private TextField histoireOlymp;
    @FXML
    private TextField resume;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> sportData = FXCollections.observableArrayList();
    private boolean isUpdateMode = false;
    private ObservableList<String> selectedRow = null;

    @FXML
    private void initialize() {
        sportIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        descrColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        titre1Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        resumColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        titre2Column.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        histoireOlympColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));

        loadSportDataFromDatabase();
        tableView.setItems(sportData);
    }

    private void loadSportDataFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Sport")) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("id"));
                row.add(rs.getString("nom"));
                row.add(rs.getString("text1"));
                row.add(rs.getString("tire1"));
                row.add(rs.getString("text2"));
                row.add(rs.getString("titre2"));
                row.add(rs.getString("text3"));
                sportData.add(row);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addBtn(MouseEvent mouseEvent) {
        if (nom.getText().isEmpty() || description.getText().isEmpty() ||
                histoireOlymp.getText().isEmpty() || resume.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            int lastSportId = getLastSportIdFromDatabase();

            ObservableList<String> row = FXCollections.observableArrayList(
                    String.valueOf(lastSportId + 1),
                    nom.getText(),
                    description.getText(),
                    "EN BREF",
                    resume.getText(),
                    "HISTOIRE OLYMPIQUE",
                    histoireOlymp.getText()
            );
            sportData.add(row);

            clearTextFields();

            addSportToDatabase(row);
        }
    }

    private int getLastSportIdFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM Sport")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void addSportToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Sport (id, nom, text1, tire1, text2, titre2, text3) VALUES (?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            stmt.setString(5, row.get(4));
            stmt.setString(6, row.get(5));
            stmt.setString(7, row.get(6));

            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBtn(MouseEvent mouseEvent) {
        if (!isUpdateMode) {
            selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                nom.setText(selectedRow.get(1));
                description.setText(selectedRow.get(2));
                resume.setText(selectedRow.get(4));
                histoireOlymp.setText(selectedRow.get(6));
                isUpdateMode = true;
            }
        } else {
            if (selectedRow != null) {
                selectedRow.set(1, nom.getText());
                selectedRow.set(2, description.getText());
                selectedRow.set(4, resume.getText());
                selectedRow.set(6, histoireOlymp.getText());

                updateSportInDatabase(selectedRow.get(0), nom.getText(), description.getText(), resume.getText(), histoireOlymp.getText());

                tableView.refresh();

                clearTextFields();
                isUpdateMode = false;
                selectedRow = null;
            }
        }
    }

    private void updateSportInDatabase(String sportId, String nom, String text1Value, String text2Value, String text3Value) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Sport SET nom=?, text1=?, tire1=?, text2=?, titre2=?, text3=? WHERE id=?")) {

            stmt.setString(1, nom);
            stmt.setString(2, text1Value);
            stmt.setString(3, "EN BREF");
            stmt.setString(4, text2Value);
            stmt.setString(5, "HISTOIRE OLYMPIQUE");
            stmt.setString(6, text3Value);
            stmt.setString(7, sportId);

            stmt.executeUpdate();
            clearTextFields();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            sportData.remove(selectedRow);
            deleteSportFromDatabase(selectedRow.get(0));
        }
    }

    private void deleteSportFromDatabase(String sportId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Sport WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(sportId));
            stmt.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void clearBtn(MouseEvent mouseEvent) {
        clearTextFields();
        wrongLabel.setText("");
        isUpdateMode = false;
    }

    public void saveBtn(MouseEvent mouseEvent) {
        // Implémentation du bouton de sauvegarde
    }

    private void clearTextFields() {
        nom.clear();
        description.clear();
        resume.clear();
        histoireOlymp.clear();
    }
}
