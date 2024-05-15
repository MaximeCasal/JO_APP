package com.appjo.app_jo.Modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.scene.control.Alert;

public class User {

    private String nom;
    private String prenom;
    private String pays;
    private String identifiant;
    private String mail;
    private String mot_passe;
    private String telephone;
    private String genre;
    private final int numeroUser = (int) Math.random() * 1000 + 1;

    public User(String nom, String prenom, String pays, String identifiant, String mail, String mot_passe, String telephone, String genre) {
        setNom(nom);
        setPrenom(prenom);
        setPays(pays);
        setIdentifiant(identifiant);
        setMail(mail);
        setMotDePasse(mot_passe);
        setTelephone(telephone);
        setGenre(genre);
    }

    public String getNom()         {return nom;}
    public String getPrenom()      { return prenom;}
    public String getPays()        { return pays;}
    public String getIdentifiant() { return identifiant;}
    public String getMail()        { return mail;}
    public String getMotDePasse()  { return mot_passe;}
    public String getTelephone()   { return telephone;}
    public String getGenre()       { return genre;}
    public int getNumeroUser()     { return numeroUser;}

    public void setNom(String Nom)                 { this.nom = Nom;}
    public void setPrenom(String Prenom)           { this.prenom  = Prenom;}
    public void setPays(String Pays)               { this.pays = Pays;}
    public void setIdentifiant(String Identifiant) { this.identifiant = Identifiant;}
    public void setMail(String Mail)               { this.mail = Mail;}
    public void setMotDePasse(String Mot_Passe)    { this.mot_passe = Mot_Passe;}
    public void setTelephone(String Telephone)     { this.telephone = Telephone;}
    public void setGenre(String Genre)             { this.genre =  Genre;}


    public void saveUser() throws IOException {
        String csvFilePath = "Modele/User.csv";

        try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            bufferWriter.write(getNom() + "," + getPrenom() +"," +getPays()+","+getIdentifiant()+","+getMotDePasse()+","+getTelephone()+","+getGenre()+"\n");

            } catch( IOException e) {
                System.err.println("Erreur lors de l'enregistrement des informations de l'utilisateur dans le fichier CSV: " + e.getMessage());
                afficherMessage("Erreur lors de l'inscription", "Une erreur est subvenue, veuillez re essayer");
            }
    }

    /*  public static User readUser(String name, String firstName) {
        String csvFilePath = "Modele/User.csv";
        String line;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Lire la première ligne pour obtenir les en-têtes des colonnes
            String delimiter = ",";
                
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne
                String Prenom = parts[1].trim();
                String Pays = parts[2].trim();
                String Age = parts[3].trim();
                String Genre = parts[4].trim();
                String Taille = parts[5].trim();
                String Poids = parts[6].trim(); 

                if (name.trim().equalsIgnoreCase(Nom) && firstName.trim().equalsIgnoreCase(Prenom)) {
                    // Correspondance trouvée, retournez les informations de l'athlete
                    System.out.println("Correspondance trouvé");
                    System.out.println(Nom +", " +Prenom+", "+Age+", "+Genre+", "+Taille+", "+Poids+"." );
                    return new User(Nom, Prenom, Pays, Age, Genre, Taille, Poids);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

        // Aucune correspondance trouvée pour les informations d'authentification fournies
        System.out.println("Aucune correspondance");
        return null;
    }    */

    public static void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
