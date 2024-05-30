    package com.appjo.app_jo.Controlleur;


    import com.appjo.app_jo.Modele.DatabaseConnection;
    import javafx.fxml.FXML;
    import javafx.fxml.FXMLLoader;
    import javafx.scene.Parent;
    import javafx.scene.Scene;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.scene.input.MouseEvent;
    import javafx.stage.Stage;

    import java.io.IOException;
    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

    public class InscriptionController {

        @FXML
        private Label wrongLogin;
        @FXML
        private TextField nom;
        @FXML
        private TextField prenom;
        @FXML
        private TextField email;
        @FXML
        private TextField identifiant;
        @FXML
        private TextField motdepasse;
        @FXML
        private TextField sexe;
        @FXML
        private TextField pays;
        @FXML
        private TextField telephone;



        public void userLog(MouseEvent mouseEvent) throws IOException {
            String name = this.nom.getText();
            String firstname = this.prenom.getText();
            String mail = this.email.getText();
            String username = this.identifiant.getText();
            String mdp = this.motdepasse.getText();
            String genre = this.sexe.getText();
            String country = this.pays.getText();
            String phone = this.telephone.getText();

            if(name.isEmpty() || firstname.isEmpty() || mail.isEmpty() || username.isEmpty()
            || mdp.isEmpty() || genre.isEmpty() || country.isEmpty() || phone.isEmpty()) {
                wrongLogin.setText("Veuillez remplir les champs");
                System.out.println("Veuillez remplir les champs");
                return;
            }
            String role = "Utilisateur";
            if(verificationInscription(name, firstname, mail, mdp, genre, country,  role, username, phone)) {
                loadScene(mouseEvent, "/com/appjo/app_jo/PrimaryScene.fxml");
            } else {
                wrongLogin.setText("Erreur de connexion");
            }
        }

        public boolean verificationInscription(String name, String firstname,String mail, String mdp, String genre,
                                                String country, String role, String username, String phone) {
            // Vérification si un compte existe déjà avec le même nom et prénom
            String queryCheckName = "SELECT * FROM Utilisateur WHERE nom = ? AND prenom = ?";
            // Vérification si un compte existe déjà avec le même nom d'utilisateur
            String queryCheckUsername = "SELECT * FROM Utilisateur WHERE nom_utilisateur = ?";
            // Vérification si un compte existe déjà avec le même numéro de téléphone
            String queryCheckPhone = "SELECT * FROM Utilisateur WHERE telephone = ?";

            try (Connection connection = DatabaseConnection.getConnection();
                 PreparedStatement statementCheckName = connection.prepareStatement(queryCheckName);
                 PreparedStatement statementCheckUsername = connection.prepareStatement(queryCheckUsername);
                 PreparedStatement statementCheckPhone = connection.prepareStatement(queryCheckPhone)) {

                // Vérification du nom et prénom
                statementCheckName.setString(1, name);
                statementCheckName.setString(2, firstname);
                ResultSet resultSetName = statementCheckName.executeQuery();
                if (resultSetName.next()) {
                    System.out.println("Un compte existe déjà avec ce nom et prénom.");
                    return false;
                }

                // Vérification du nom d'utilisateur
                statementCheckUsername.setString(1, username);
                ResultSet resultSetUsername = statementCheckUsername.executeQuery();
                if (resultSetUsername.next()) {
                    System.out.println("Un compte existe déjà avec ce nom d'utilisateur.");
                    return false;
                }

                // Vérification du numéro de téléphone
                statementCheckPhone.setString(1, phone);
                ResultSet resultSetPhone = statementCheckPhone.executeQuery();
                if (resultSetPhone.next()) {
                    System.out.println("Un compte existe déjà avec ce numéro de téléphone.");
                    return false;
                }

                // Si aucune des vérifications n'échoue, alors procéder à l'insertion
                String queryInsert = "INSERT INTO Utilisateur (nom, prenom, email, mot_de_passe, sexe, pays, role, nom_utilisateur, telephone)" +
                        "VALUES(?,?,?,?,?,?,?,?,?)";
                try (PreparedStatement statementInsert = connection.prepareStatement(queryInsert)) {
                    statementInsert.setString(1, name);
                    statementInsert.setString(2, firstname);
                    statementInsert.setString(3, mail);
                    statementInsert.setString(4, mdp);
                    statementInsert.setString(5, genre);
                    statementInsert.setString(6, country);
                    statementInsert.setString(7, role);
                    statementInsert.setString(8, username);
                    statementInsert.setString(9, phone);

                    int rowsAffected = statementInsert.executeUpdate();
                    return rowsAffected > 0;
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

    }
