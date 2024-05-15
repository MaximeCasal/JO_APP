package com.appjo.app_jo.Controlleur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.appjo.app_jo.Modele.User;

import java.sql.*;
import java.time.LocalDateTime;


public class ConnexionController {

    @FXML
    private TextField co_identifiant;
    @FXML
    private TextField co_mdp;
    @FXML 
    private TextField inscr_nom;
    @FXML 
    private TextField inscr_prenom;
    @FXML 
    private TextField inscr_identifiant;
    @FXML 
    private TextField inscr_mail;
    @FXML 
    private TextField inscr_mdp;
    @FXML 
    private TextField inscr_confmdp;
    @FXML 
    private TextField inscr_telephone;
    @FXML 
    private TextField inscr_genre;
    @FXML 
    private TextField inscr_pays;


    public static final String csvFilePath = "src/main/java/com/joapp/jo_app/Controlleur/User.csv";
    
    @FXML
    private void handleConnexion(ActionEvent event) {
        String identifiant =  co_identifiant.getText();
        String  mdp = co_mdp.getText();

        boolean connexionReussie =  verificationConnexion(identifiant, mdp);

        if(!connexionReussie) {
            System.out.println("Connexion réussie");
        } else {
            afficherMessage("Erreur de connexion", "Mot de Passe ou Identifiant incorrect");
            return; 
        }

        if(connexionReussie) {
        // Charger la nouvelle page
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../SceneBuilder/PrimaryScene.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            
            // Récupérer la référence au Stage
            Stage stage = (Stage) co_identifiant.getScene().getWindow();
            
            // Configurer la nouvelle scène et afficher
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println(" " + e.getMessage());
        }
    } else {
        afficherMessage("Erreur de connexion", "Mot de Passe ou Identifiant incorrect");
        return; 
    }

    }

    public static boolean verificationConnexion(String identifiant, String motDePasse) {
        String query = "SELECT * FROM users WHERE identifiant = ? AND mot_de_passe = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, identifiant);
            preparedStatement.setString(2, motDePasse);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next(); // Retourne true si l'utilisateur est trouvé dans la base de données
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    private void handleInscription(ActionEvent event) throws IOException {
        String nom = inscr_nom.getText();
        String prenom = inscr_prenom.getText();
        String nom_utilisateur = inscr_identifiant.getText();
        String pays = inscr_pays.getText();
        String email  = inscr_mail.getText();
        String mot_de_passe = inscr_mdp.getText();
        String confirmationMdp = inscr_confmdp.getText();
        String telephone = inscr_telephone.getText();
        String genre = inscr_genre.getText();
        String role = "utilisateur";
        String date = String.valueOf(LocalDateTime.now()); // Assurez-vous que cette méthode est correctement implémentée

        if (nom.isEmpty() || prenom.isEmpty() || nom_utilisateur.isEmpty() || email.isEmpty() || mot_de_passe.isEmpty() || confirmationMdp.isEmpty() || telephone.isEmpty() || genre.isEmpty()) {
            // Afficher un message d'erreur ou indiquer à l'utilisateur de remplir tous les champs obligatoires
            return;
        }


        String query = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, sexe, pays, role, nom_utilisateur, date_creation, telephone) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, mot_de_passe);
            statement.setString(5, genre);
            statement.setString(6, pays);
            statement.setString(7, role);
            statement.setString(8, nom_utilisateur);
            statement.setString(9, date);
            statement.setString(10, telephone);

            int rowsInserted = statement.executeUpdate();

            if (verificationInscription(nom, prenom, nom_utilisateur, email, mot_de_passe , confirmationMdp)  ) {
                // L'insertion a réussi, vous pouvez afficher un message de succès à l'utilisateur
                afficherMessage("Création réussie","Votre compte a été créée avec succés");
            } else {
                // L'insertion a échoué, affichez un message d'erreur à l'utilisateur
                afficherMessage("Création échoué","Une erreur est survenue lors de la création de votre compte");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérer l'erreur de manière appropriée, par exemple en affichant un message à l'utilisateur
        }
    }

    public static boolean verificationInscription(String nom, String prenom, String nom_utilisateur, String email, String mdp, String confMdp) {
        String query = "SELECT email, nom_utilisateur FROM Utilisateur WHERE email = ? OR nom_utilisateur = ?";

        // Validation des données d'entrée
        if (!isValidEmail(email)) {
            afficherMessage("Erreur", "Format d'email invalide");
            return false;
        }
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query))  {

            statement.setString(1, email);
            statement.setString(2, nom_utilisateur);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Un compte existe déjà avec cet email
                afficherMessage("Erreur", "Un compte utilise déjà cette adresse e-mail ou nom d'utilisateur");
                return false;
            }

            // Vérifier si les mots de passe correspondent
            if (!mdp.equals(confMdp)) {
                afficherMessage("Erreur", "Les mots de passe ne correspondent pas");
                return false;
            }

            // Si aucun compte n'utilise cet email et les mots de passe correspondent, la vérification est réussie
            afficherMessage("Création réussie","Votre compte à bien été créé");
            return true;

        } catch (SQLException | IOException e) {
            // Gérer l'erreur SQL, par exemple en affichant un message d'erreur ou en journalisant l'erreur
            System.err.println("Erreur lors de la vérification de l'inscription dans la base de données: " + e.getMessage());
            return false;
        }
    }

    private static boolean isValidEmail(String email) {
        String regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(regex);
    }


    public static void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}