package com.appjo.app_jo.Controlleur.Admin;

import com.appjo.app_jo.Modele.DatabaseConnection;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.UnitValue;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;

public class AdminResultatController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> resultatIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> sportColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> athColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> tempsColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> placementColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> medailleColumn;

    @FXML
    private TextField sport;
    @FXML
    private TextField athlete;
    @FXML
    private TextField temps;
    @FXML
    private TextField placement;
    @FXML
    private TextField medaille;
    @FXML
    private Label wrongLabel;

    private ObservableList<ObservableList<String>> resultatData = FXCollections.observableArrayList();
    private boolean isUpdateMode = false; // Variable to track update mode
    private ObservableList<String> selectedRow = null; // Variable to store the selected row

    @FXML
    private void initialize() {
        // Initialize the columns
        resultatIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        sportColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        athColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        tempsColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        placementColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        medailleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));

        // Load data from the database
        loadResultatDataFromDatabase();

        // Set the table data
        tableView.setItems(resultatData);
    }

    private void loadResultatDataFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Resultat")) {

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(rs.getString("id"));
                row.add(rs.getString("sport"));
                row.add(rs.getString("athlete"));
                row.add(rs.getString("temps"));
                row.add(rs.getString("placement"));
                row.add(rs.getString("medaille"));
                resultatData.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addBtn(MouseEvent mouseEvent) {
        if (sport.getText().isEmpty() || athlete.getText().isEmpty() ||
                placement.getText().isEmpty() || medaille.getText().isEmpty() ||
                sport.getText().isEmpty()) {
            wrongLabel.setText("Tous les champs doivent être remplis");
        } else {
            int sportId = getSportId(sport.getText());
            int athId = getAthId(athlete.getText());
            if (sportId == 0) {
                wrongLabel.setText("Sport non trouvé dans la base de données.");
                return;
            }
            if(athId == 0) {
                wrongLabel.setText("Athlète non trouvé dans la base de donnée");
            }

            int lastResultatId = getLastResultatIdFromDatabase();

            ObservableList<String> row = FXCollections.observableArrayList(
                    String.valueOf(lastResultatId + 1),
                    sport.getText(),
                    athlete.getText(),
                    temps.getText(),
                    placement.getText(),
                    medaille.getText()
            );
            resultatData.add(row);

            clearTextFields();

            addResultatToDatabase(row);
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
    private int getAthId(String sportName) {
        String query = "SELECT Id_athlete FROM Athlete WHERE nom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, sportName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("Id_athlete");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if the sport is not found
    }

    private int getLastResultatIdFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM Resultat")) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return 0; // Return 0 if there are no resultat in the database
    }

    private void addResultatToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Resultat (id, sport, athlete, temps, placement, medaille) VALUES (?, ?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            stmt.setString(5, row.get(4));
            stmt.setString(6, row.get(5));

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
                sport.setText(selectedRow.get(1));
                athlete.setText(selectedRow.get(2));
                temps.setText(selectedRow.get(3));
                placement.setText(selectedRow.get(4));
                medaille.setText(selectedRow.get(5));
                isUpdateMode = true;
            }
        } else {
            // Second click: update the selected row in the database
            if (selectedRow != null) {
                selectedRow.set(1, sport.getText());
                selectedRow.set(2, athlete.getText());
                selectedRow.set(3, temps.getText());
                selectedRow.set(4, placement.getText());
                selectedRow.set(5, medaille.getText());

                updateRsultatInDatabase(selectedRow.get(0), sport.getText(), athlete.getText(), temps.getText(), placement.getText(), medaille.getText());

                tableView.refresh();

                clearTextFields();
                isUpdateMode = false;
                selectedRow = null;
            }
        }
    }

    private void updateRsultatInDatabase(String resultatId, String sportValue, String ahtleteValue, String tempsValue, String placementValue, String medailleValue) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Resultat SET sport=?, athlete=?, temps=?, placement=?, medaille=? WHERE id=?")) {

            stmt.setString(1, sportValue);
            stmt.setString(2, ahtleteValue);
            stmt.setString(3, tempsValue);
            stmt.setString(4, placementValue);
            stmt.setString(5, medailleValue);
            stmt.setString(6, resultatId);

            stmt.executeUpdate();

            // Effacer les champs de texte après la mise à jour
            clearTextFields();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
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

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            resultatData.remove(selectedRow);
            deleteResultatFromDatabase(selectedRow.get(0));
        }
    }

    private void deleteResultatFromDatabase(String athleteId) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Resultat WHERE id = ?")) {

            stmt.setInt(1, Integer.parseInt(athleteId));
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

    private void clearTextFields() {
        // Effacer les champs de texte après la mise à jour
        sport.clear();
        athlete.clear();
        temps.clear();
        placement.clear();
        medaille.clear();
    }

    public void pageResultatAdmin(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Admin/AdminResultat.fxml");
    }

    private void handleDownloadPdf() {
        String pdfPath = "resultat_details.pdf";
        try {
            PdfWriter writer = new PdfWriter(pdfPath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Add title
            document.add(new Paragraph("Details des Résultats").setBold().setFontSize(18));

            // Create a table with the appropriate number of columns
            Table table = new Table(UnitValue.createPercentArray(new float[]{1, 2, 2, 2, 2, 2}));
            table.setWidth(UnitValue.createPercentValue(100));

            // Add table headers
            table.addHeaderCell("ID");
            table.addHeaderCell("Sport");
            table.addHeaderCell("Athlete");
            table.addHeaderCell("Temps");
            table.addHeaderCell("Placement");
            table.addHeaderCell("Medaille");

            // Add rows from the TableView data
            for (ObservableList<String> row : resultatData) {
                for (String cell : row) {
                    table.addCell(new Paragraph(cell));
                }
            }

            document.add(table);
            document.close();
            System.out.println("PDF created at " + pdfPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
