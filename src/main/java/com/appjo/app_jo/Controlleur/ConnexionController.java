package com.appjo.app_jo.Controlleur;

import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnexionController {

    @FXML
    private Button signin;
    @FXML
    private Label wrongLogin;
    @FXML
    private TextField identifiant;
    @FXML
    private PasswordField motdepasse;

    public void userLogin(MouseEvent event) throws IOException {
        String username = identifiant.getText();
        String mdp = motdepasse.getText();

        if (username.isEmpty() || mdp.isEmpty()) {
            wrongLogin.setText("Veuillez remplir les champs");
            System.out.println("Veuillez remplir les champs");
            return;
        }

        if (verificationConnexion(username, mdp)) {
            if (isAdmin(username, mdp)) {
                loadScene(event, "/com/appjo/app_jo/Admin/AdminSportDetails.fxml");
            } else {
                loadScene(event, "/com/appjo/app_jo/PrimaryScene.fxml");
            }
        } else {
            wrongLogin.setText("Nom d'utilisateur ou mot de passe incorrect.");
        }
    }

    private boolean verificationConnexion(String username, String mdp) {
        String query = "SELECT * FROM Utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, mdp);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isAdmin(String username, String mdp) {
        String query = "SELECT role FROM Utilisateur WHERE nom_utilisateur = ? AND mot_de_passe = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, mdp);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role");
                return "admin".equals(role);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
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
            wrongLogin.setText("Erreur lors du chargement de la page.");
        }
    }

    public void inscrire(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Inscription.fxml");
    }
}
