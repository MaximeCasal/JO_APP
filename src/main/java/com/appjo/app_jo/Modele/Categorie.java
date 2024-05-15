package com.appjo.app_jo.Modele;

public class Categorie extends Sport {

    private String poids;
    private String taille;

    public Categorie(String name, int nombreAthletes, String poids, String taille) {
        super(name, nombreAthletes);
        setPoids(poids);
        setTaille(taille);
    }

    public String getPoids()  { return poids;}
    public String getTaille() { return taille;}

    public void setPoids(String Poids)   { this.poids  = Poids;}
    public void setTaille(String Taille) { this.taille = Taille;}
    
}
