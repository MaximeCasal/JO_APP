
/* package com.appjo.app_jo.Modele;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Athletes {
    private String name;
    private String firstName;
    private String country;
    private String age;
    private String gender;
    private String size;
    private String weight;

    public Athletes(String name, String firstName, String country, String age, String gender, String size, String weight) throws IOException {
        setName(name);
        setFirstName(firstName);
        setCountry(country);
        setAge(age);
        setGender(gender);
        setSize(size);
        setWeight(weight);
    }

    public String getName()      { return name;}
    public String getFirstName() { return firstName;}
    public String getCountry()   { return country;}
    public String getAge()       { return age;}
    public String getGender()    { return gender;}
    public String getSize()      { return size;}
    public String getWeight()    { return weight;}

    public void setName(String Nom)         { this.name    = Nom;}
    public void setFirstName(String Prenom) { this.firstName = Prenom;}
    public void setCountry(String Pays)     { this.country = Pays;}
    public void setAge(String Age)          { this.age     = Age;}
    public void setGender(String Genre)     { this.gender  = Genre;}
    public void setSize(String Taille)      { this.size    = Taille;}
    public void setWeight(String Poids)     { this.weight  = Poids;}

    public void saveAthlete() throws IOException {
        String csvFilePath = "JavaProject/Athletes.csv";
        boolean athleteExist = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            // Lire la première ligne pour obtenir les en-têtes des colonnes
            String line;
            String delimiter = ",";
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne
                String Prenom = parts[1].trim();

                if (getName().trim().equalsIgnoreCase(Nom) && getFirstName().trim().equalsIgnoreCase(Prenom)) {
                    // Correspondance trouvée, retournez les informations de l'athlete
                    System.out.println("L'athlète est déjà enregistré ");
                    athleteExist = true;
                    break;
                }
            }
            } catch (IOException e) {
                System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

            if(!athleteExist) {
                try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(csvFilePath, true))) {
                    bufferWriter.write(getName() + "," + getFirstName() +"," +getCountry()+","+getAge()+","+getGender()+","+getSize()+","+getWeight()+"\n");
                    System.out.println("Les informations ont bien été enregistrés");
                } catch( IOException e) {
                    System.err.println("Erreur lors de l'enregistrement des informations de l'utilisateur dans le fichier CSV: " + e.getMessage());
                }
            }
    }

    public static Athletes lireAthlete(String name, String firstName) {
        String csvFilePath = "JavaProject/Athletes.csv";
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
                    return new Athletes(Nom, Prenom, Pays, Age, Genre, Taille, Poids);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
        }

        // Aucune correspondance trouvée pour les informations d'authentification fournies
        System.out.println("Aucune correspondance");
        return null;
    }


    public static void removeAthlete(String name, String firstName) {
        String csvFilePath = "JavaProject/Athletes.csv";
        List<String> linesToKeep = new ArrayList<>();
        boolean athleteFound = false;

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
                String Prenom = parts[1].trim();

                if (name.trim().equalsIgnoreCase(Nom) && firstName.trim().equalsIgnoreCase(Prenom)) {
                    // Correspondance trouvée, ne pas conserver cette ligne
                    System.out.println("L'athlète a été supprimé.");
                    athleteFound = true;
                } else {
                    // Conserver cette ligne
                    linesToKeep.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return;
        }

        if (athleteFound) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFilePath))) {
                // Écrire toutes les lignes conservées dans le fichier CSV
                for (String line : linesToKeep) {
                    writer.write(line + "\n");
                }
                System.out.println("Le fichier a été mis à jour avec l'athlète supprimé.");
            } catch (IOException e) {
                System.err.println("Erreur lors de l'écriture dans le fichier CSV : " + e.getMessage());
            }
        } else {
            System.out.println("Aucun athlète trouvé avec le nom et le prénom spécifiés.");
        }
    }

    public static Athletes updateAthelete(String nom, String prenom, String pays, String age, String genre, String taille, String poids) throws IOException {
        String csvFilePath = "JavaProject/Athletes.csv";
        String line;
        boolean athleteExist = false;
        Athletes athlete = null; // Déclaration de la variable en dehors des blocs if/else
    
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String delimiter = ",";
            // Lire les lignes suivantes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(delimiter); 
                String Nom = parts[0].trim();       // trim() permet de retirer les blancs en début et fin de chaîne
                String Prenom = parts[1].trim();
    
                if (nom.trim().equalsIgnoreCase(Nom) && prenom.trim().equalsIgnoreCase(Prenom)) {
                    // Correspondance trouvée, mettre à jour les informations de l'athlète
                    System.out.println("L'athlète existe.");
                    athleteExist = true;
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier CSV : " + e.getMessage());
            return null;
        }
    
        if (athleteExist) {
            // Supprimer l'athlète existant
            removeAthlete(nom, prenom);
            athlete = new Athletes(nom, prenom, pays, age, genre, taille, poids);
            athlete.saveAthlete();
            System.out.println("Les informations de l'athlète ont été mises à jour.");
        } else {
            athlete = new Athletes(nom, prenom, pays, age, genre, taille, poids);
            athlete.saveAthlete();
            System.out.println("L'athlète a été ajouté avec succès.");
        }
    
        return athlete;
    }
    
    
}

*/
