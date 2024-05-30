package com.appjo.app_jo.Controlleur;

import javafx.beans.property.*;

public class Athlete {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty nom = new SimpleStringProperty(this, "nom");
    private final StringProperty prenom = new SimpleStringProperty(this, "prenom");
    private final StringProperty pays = new SimpleStringProperty(this, "pays");
    private final StringProperty sexe = new SimpleStringProperty(this, "sexe");
    private final DoubleProperty taille = new SimpleDoubleProperty(this, "taille");
    private final IntegerProperty poids = new SimpleIntegerProperty(this, "poids");
    private final StringProperty sport = new SimpleStringProperty(this, "sport");
    private final IntegerProperty age = new SimpleIntegerProperty(this, "age");
    private final StringProperty categorie = new SimpleStringProperty(this, "categorie");

    public Athlete(int id, String nom, String prenom, String pays, String sexe, double taille, int poids, String sport, int age, String categorie) {
        this.id.set(id);
        this.nom.set(nom);
        this.prenom.set(prenom);
        this.pays.set(pays);
        this.sexe.set(sexe);
        this.taille.set(taille);
        this.poids.set(poids);
        this.sport.set(sport);
        this.age.set(age);
        this.categorie.set(categorie);
    }

    // Getters and setters for all properties...

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public String getPays() {
        return pays.get();
    }

    public StringProperty paysProperty() {
        return pays;
    }

    public String getSexe() {
        return sexe.get();
    }

    public StringProperty sexeProperty() {
        return sexe;
    }

    public double getTaille() {
        return taille.get();
    }

    public DoubleProperty tailleProperty() {
        return taille;
    }

    public int getPoids() {
        return poids.get();
    }

    public IntegerProperty poidsProperty() {
        return poids;
    }

    public String getSport() {
        return sport.get();
    }

    public StringProperty sportProperty() {
        return sport;
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getCategorie() {
        return categorie.get();
    }

    public StringProperty categorieProperty() {
        return categorie;
    }
}
