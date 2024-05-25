package com.appjo.app_jo.Controlleur;

import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

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

        if (identifiant.getText().isEmpty() || motdepasse.getText().isEmpty()) {
            wrongLogin.setText("Veuillez remplir les champs");
            System.out.println("Veuillez remplir les champs");
            return;
        }

        if (verificationConnexion(username, mdp)) {
            // Code pour rediriger l'utilisateur vers une autre page ou indiquer le succès de la connexion
            System.out.println("Connexion Réussie");
        } else {
            wrongLogin.setText("Nom d'utilisateur ou mot de passe incorrect.");
            System.out.println("Identifiant ou mot de passe incorrect.");
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
}
