package com.appjo.app_jo.Modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sport   {

    private String name;
    private int nombreAthletes;

    public Sport(String name, int nombreAthletes) {
        setName(name);
        setNombreAthlètes(nombreAthletes);
    }

    public String getName()        { return name;}
    public int getNombreAthletes() { return nombreAthletes;}

    public void setName(String nom)                    { this.name           = nom; }
    public void setNombreAthlètes(int nombreDAthletes) { 
        if(nombreDAthletes >= 0) {
            this.nombreAthletes = nombreDAthletes;
        }
        else {
            System.out.println("Un sport doit avoir un nombre positif d'athlète");
        }
    }
    
    public void saveSport() throws IOException {
        String csvFilePath = "JavaProject/Sport.csv";
        boolean sportExist = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Lire la première ligne pour obtenir les en-têtes des colonnes
            String line;
            String delimiter = ",";
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne
                String nombreAthlete = parts[1].trim();

                if (getName().trim().equalsIgnoreCase(Nom)) {
                    // Correspondance trouvée, retournez les informations de l'athlete
                    System.out.println("Le sport est déjà enregistré ");
                    System.out.println("Voici les informations sur ce sport : " +Nom+", " +nombreAthlete +" athlètes");
                    sportExist = true;
                    break;
                }
            }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

            if(!sportExist) {
                try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(csvFilePath, true))) {
                    bufferWriter.write(getName() + "," + getNombreAthletes()+ "\n");
                    System.out.println("Les informations ont bien été enregistrés");
                } catch( IOException e) {
                    System.err.println("Erreur lors de l'enregistrement des informations de l'utilisateur dans le fichier CSV: " + e.getMessage());
                }
            }
    }

    public static void removeSport(String name, int nombreAthlete) {
        String csvFilePath = "JavaProject/Sport.csv";
        List<String> linesToKeep = new ArrayList<>();
        boolean sportFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Lire la première ligne pour obtenir les en-têtes des colonnes
            String headers = reader.readLine();
            linesToKeep.add(headers);

            String line;
            String delimiter = ",";
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne

                if (name.trim().equalsIgnoreCase(Nom)) {
                    // Correspondance trouvée, ne pas conserver cette ligne
                    System.out.println("L'athlète a été supprimé.");
                    sportFound = true;
                } else {
                    // Conserver cette ligne
                    linesToKeep.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return;
        }

        if (sportFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
                // Écrire toutes les lignes conservées dans le fichier CSV
                for (String line : linesToKeep) {
                    writer.write(line + "\n");
                }
                System.out.println("Le fichier a été mis à jour avec le sport supprimé.");
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture dans le fichier CSV : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun sport trouvé avec le nom spécifiés.");
        }
    }

    public static Sport updateSport(String nom, int nombreAthlete) throws IOException{
        String csvFilePath = "JavaProject/Sport.csv";
        String line;
        boolean sportExist = false;
        Sport sport = null;
    
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String delimiter = ",";
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne
    
                if (nom.trim().equalsIgnoreCase(Nom)) {
                    // Correspondance trouvée, mettre à jour les informations de l'athlète
                    System.out.println("Le sport existe.");
                    sportExist = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return null;
        }
    
        if (sportExist) {
            // Supprimer l'athlète existant
            removeSport(nom, nombreAthlete);
            sport = new Sport(nom, nombreAthlete);
            sport.saveSport();
            System.out.println("Les informations du sport ont été mises à jour.");
        } else {
            sport = new Sport(nom, nombreAthlete);
            sport.saveSport();
            System.out.println("Le sport a été ajouté avec succès.");
        }
    
        return sport;
    }
    
}
