package com.appjo.app_jo.Controlleur;

import javafx.beans.property.*;

public class Athlete {
    private final IntegerProperty id = new SimpleIntegerProperty(this, "id");
    private final StringProperty nom = new SimpleStringProperty(this, "nom");
    private final StringProperty prenom = new SimpleStringProperty(this, "prenom");

    public Athlete(int id, String nom, String prenom) {
        this.id.set(id);
        this.nom.set(nom);
        this.prenom.set(prenom);
    }

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
}
