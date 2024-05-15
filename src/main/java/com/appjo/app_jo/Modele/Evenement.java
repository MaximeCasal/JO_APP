/* package com.appjo.app_jo.Modele;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;
import java.util.Date;


import java.io.IOException;

public class Evenement {

    @FXML
    private VBox athletesContainer;
    @FXML
    private VBox EventContainer;
    
    private String name;
    private String lieu;
    private Date date;
    private String sportName;
    private List<Athletes> athlete;

    public Evenement(String name, String lieu, Date date, String sportName, List<Athletes> athlete) {
        setName(name);
        setLieu(lieu);
        setDate(date);
        setSportName(sportName);
        setAthlete(athlete);
    }

    public String getName()            { return name;}
    public String getLieu()            { return lieu; };
    public Date getDate()              { return date;}
    public String getSportName()       { return sportName;}
    public List<Athletes> getAthlete() { return athlete;}

    public void setName(String nom)                { this.name      = nom;}
    public void setLieu(String lieu)               { this.lieu      = lieu;}
    public void setDate(Date date)                 { this.date      = date;}
    public void setSportName(String nomSport)      { this.sportName = nomSport;}
    public void setAthlete(List<Athletes> Athlete) { this.athlete   = Athlete;} 

    @FXML
    public void handleCreerEvenement(ActionEvent event) throws IOException {

        HBox eventRow = new HBox(50);
        eventRow.setPadding(new Insets(20));

        TextField nomEventField = new TextField();
        nomEventField.setPromptText("Nom de l'évènement");
    
        handleAjoutAthlete(event);

        TextField lieuEventField = new TextField();
        lieuEventField.setPromptText("Lieu de l'évènement");

        DatePicker dateEventField = new DatePicker();
        dateEventField.setPromptText("Date de l'évènement");

        Button ajoutEvent = new Button("Ajouter cet évènement");

        athletesContainer.getChildren().add(eventRow);

        boolean evenementValide = ajouterEvenement(eventRow);

        if(evenementValide) {
            Evenement evenement = new Evenement(name, athlete, sportName);
            evenement.saveEvenement();
            afficherMessage("Ajout reussi", "L'évènement à bien été créer");
        }
    }

    public boolean ajouterEvenement(HBox eventRow) {
        boolean evenementValide = false;

        TextField nomEventTextField = (TextField) eventRow.getChildren().get(0);
        String nomEvent = nomEventTextField.getText();
        Athletes athletes = (Athletes) eventRow.getChildren().get(1);
        boolean dateValide = dateValide(eventRow);

        if(nomEvent != null) {
            if(athletes.count() > 0) {
                if(dateValide) {
                    evenementValide = true;
                    afficherMessage("Evènement créer", "L'évènement à bien été créer");
                }
            } else {
                evenementValide = false;
                afficherMessage("Erreur", "Veuillez inscrire au moins un athlète");
            }
        } else {
            evenementValide = false;
            afficherMessage("Erreur", "Veuillez entrer un nom pour l'évènement");
        }

        return evenementValide;

    }

    public boolean dateValide(HBox eventRow) {
        boolean dateValide = false;
        
        LocalDate currentDate = LocalDate.now();
        DatePicker datePicker = (DatePicker) eventRow.getChildren().get(3);
        LocalDate selectDate = datePicker.getValue();

        if(selectDate != null) {
            if(selectDate.isAfter(currentDate)) {
                dateValide = true;
            } else {
                dateValide = false;
                afficherMessage("Date incorrecte", "La date sélectionné est avant la date du jour");
            }
        }  else {
            dateValide = false;
            afficherMessage("Date incorrecte","Entrez une date");
        }

        return dateValide;


    }

    @FXML
    public void handleAjoutAthlete(ActionEvent event) throws IOException {
        HBox athleteRow = new HBox(10);
        athleteRow.setPadding(new Insets(5));

        TextField nameField = new TextField();
        nameField.setPromptText("Nom"); 

        TextField prenomField = new TextField();
        prenomField.setPromptText("Prenom"); 
        
        athleteRow.getChildren().addAll(nameField, prenomField);
        athletesContainer.getChildren().add(athleteRow);
    }

    public static void afficherMessage(String titre, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titre);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

 */