package com.appjo.app_jo.Controlleur.Admin;

import com.appjo.app_jo.Modele.DatabaseConnection;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminUtilisateurController {

    @FXML
    private TableView<ObservableList<String>> tableView;
    @FXML
    private TableColumn<ObservableList<String>, String> utilisateurIDColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> prenomColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> mailColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> mdpColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> sexeColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> paysColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> roleColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> nomUtiliColumn;
    @FXML
    private TableColumn<ObservableList<String>, String> telephoneColumn;

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField mail;
    @FXML
    private TextField sexe;
    @FXML
    private TextField mdp;
    @FXML
    private TextField pays;
    @FXML
    private TextField role;
    @FXML
    private TextField nomUtilisateur;
    @FXML
    private TextField telephone;
    @FXML
    private Label wrongLabel;
    @FXML
    private Label nbrInscrit;
    @FXML
    private Label nbrUtilisateur;
    @FXML
    private Label nbrAdmin;

    private ObservableList<ObservableList<String>> utilisateurData = FXCollections.observableArrayList();
    private boolean isUpdateMode = false; // Variable to track update mode
    private ObservableList<String> selectedRow = null; // Variable to store the selected row

    @FXML
    private void initialize() {
        utilisateurIDColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(0)));
        nomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(1)));
        prenomColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(2)));
        mailColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(3)));
        mdpColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(4)));
        sexeColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(5)));
        paysColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(6)));
        roleColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(7)));
        nomUtiliColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(8)));
        telephoneColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get(9)));

        loadUtilisateurDataFromDatabase();
        tableView.setItems(utilisateurData);

        updateNbrInscrit();
        updateNbrUtilisateur();
        updateNbrAdmin();
    }

    private void loadUtilisateurDataFromDatabase() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Utilisateur")) {

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ObservableList<String> row = FXCollections.observableArrayList(
                            rs.getString("Id_user"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("mot_de_passe"),
                            rs.getString("sexe"),
                            rs.getString("pays"),
                            rs.getString("role"),
                            rs.getString("nom_utilisateur"),
                            rs.getString("telephone")
                    );
                    utilisateurData.add(row);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addBtn(MouseEvent mouseEvent) {

        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || mail.getText().isEmpty() || mdp.getText().isEmpty()
                || sexe.getText().isEmpty() || pays.getText().isEmpty() || role.getText().isEmpty() ||
                nomUtilisateur.getText().isEmpty() || telephone.getText().isEmpty()) {
            wrongLabel.setText("Veuillez remplir tous les champs");
        } else {

            int lastUtiId = getLastUtiIdFromDataBase();
            ObservableList<String> row = FXCollections.observableArrayList(
                    String.valueOf(lastUtiId + 1),
                    nom.getText(),
                    prenom.getText(),
                    mail.getText(),
                    mdp.getText(),
                    sexe.getText(),
                    pays.getText(),
                    role.getText(),
                    nomUtilisateur.getText(),
                    telephone.getText()
            );
            utilisateurData.add(row);
            clearTextFields();
            addUtilisateurToDatabase(row);

            updateNbrInscrit();
            updateNbrUtilisateur();
            updateNbrAdmin();
        }
    }

    public void updateBtn(MouseEvent mouseEvent) {
        if (!isUpdateMode) {
            // First click: fill the text fields with selected row data
            selectedRow = tableView.getSelectionModel().getSelectedItem();
            if (selectedRow != null) {
                nom.setText(selectedRow.get(1));
                prenom.setText(selectedRow.get(2));
                mail.setText(selectedRow.get(3));
                mdp.setText(selectedRow.get(4));
                sexe.setText(selectedRow.get(5));
                pays.setText(selectedRow.get(6));
                role.setText(selectedRow.get(7));
                nomUtilisateur.setText(selectedRow.get(8));
                telephone.setText(selectedRow.get(9));
                isUpdateMode = true;
            }
        } else {
            // Second click: update the selected row in the database
            if (selectedRow != null) {
                selectedRow.set(1, nom.getText());
                selectedRow.set(2, prenom.getText());
                selectedRow.set(3, mail.getText());
                selectedRow.set(4, mdp.getText());
                selectedRow.set(5, sexe.getText());
                selectedRow.set(6, pays.getText());
                selectedRow.set(7, role.getText());
                selectedRow.set(8, nomUtilisateur.getText());
                selectedRow.set(9, telephone.getText());

                updateUtiInDatabase(selectedRow.get(0), nom.getText(), prenom.getText(), mail.getText(), mdp.getText(), sexe.getText(), pays.getText(), role.getText(), nomUtilisateur.getText(), telephone.getText());

                tableView.refresh();

                clearTextFields();
                isUpdateMode = false;
                selectedRow = null;
            }
        }

    }

    public void supprBtn(MouseEvent mouseEvent) {
        ObservableList<String> selectedRow = tableView.getSelectionModel().getSelectedItem();
        if (selectedRow != null) {
            utilisateurData.remove(selectedRow);
            deleteUtiFromDatabase(selectedRow);

            updateNbrInscrit();
            updateNbrUtilisateur();
            updateNbrAdmin();
        }
    }

    public void clearBtn(MouseEvent mouseEvent) {
        clearTextFields();
        isUpdateMode = false;
    }

    private int getLastUtiIdFromDataBase() {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT MAX(Id_user) FROM Utilisateur")) {

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

    private void addUtilisateurToDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO Utilisateur (Id_user, nom, prenom, email,mot_de_passe, sexe, pays, role, nom_utilisateur, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.setString(2, row.get(1));
            stmt.setString(3, row.get(2));
            stmt.setString(4, row.get(3));
            stmt.setString(5, row.get(4));
            stmt.setString(6, row.get(5));
            stmt.setString(7, row.get(6));
            stmt.setString(8, row.get(7));
            stmt.setString(9, row.get(8));
            stmt.setString(10, row.get(9));

            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUtiFromDatabase(ObservableList<String> row) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Utilisateur WHERE Id_user=?")) {

            stmt.setInt(1, Integer.parseInt(row.get(0)));
            stmt.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateUtiInDatabase(String utiId, String nomValue, String prenomValue, String mailValue, String mdpValue, String sexeValue, String paysValue, String roleValue, String nomUtilisateurValue, String telephoneValue) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Utilisateur SET nom=?, prenom=?, email=? ,mot_de_passe=? , sexe=? , pays=? , role=? , nom_utilisateur=? , telephone=?  WHERE Id_user=?")) {

            stmt.setString(1, nomValue);
            stmt.setString(2, prenomValue);
            stmt.setString(3, mailValue);
            stmt.setString(4, mdpValue);
            stmt.setString(5, sexeValue);
            stmt.setString(6, paysValue);
            stmt.setString(7, roleValue);
            stmt.setString(8, nomUtilisateurValue);
            stmt.setString(9, telephoneValue);
            stmt.setInt(10, Integer.parseInt(utiId));

            stmt.executeUpdate();

            // Effacer les champs de texte après la mise à jour
            clearTextFields();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    private void updateNbrInscrit() {
        int count = nbrUtilisateurDataBase();
        nbrUtilisateur.setText(String.valueOf(count));
    }

    private void updateNbrUtilisateur() {
        int count = nbrInscritDataBase();
        nbrInscrit.setText(String.valueOf(count));
    }

    private void updateNbrAdmin() {
        int count = nbrAdminDataBase();
        nbrAdmin.setText(String.valueOf(count));
    }

    private int nbrInscritDataBase() {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(Id_user) FROM Utilisateur")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int nbrUtilisateurDataBase() {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(Id_user) FROM Utilisateur WHERE role='utilisateur'")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    private int nbrAdminDataBase() {
        int count = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(Id_user) FROM Utilisateur WHERE role='admin'")) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return count;
    }


    private void clearTextFields() {
        nom.clear();
        prenom.clear();
        mail.clear();
        mdp.clear();
        sexe.clear();
        pays.clear();
        role.clear();
        nomUtilisateur.clear();
        telephone.clear();
        wrongLabel.setText("");
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
