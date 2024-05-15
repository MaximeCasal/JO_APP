package com.appjo.app_jo.Modele;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Resultat {
    private String time;
    private String score;
    private String medals;
    private int place;
    private String performance;

    public Resultat(String time, String score, String medals, int place, String performance) {
        setTime(time);
        setScore(score);
        setMedals(medals);
        setPlace(place);
        setPerformance(performance);
    }

    public String getTime()        { return time;}
    public String getScore()       { return score;}
    public String getMedals()      { return medals;}
    public int getPlace()          { return place;}
    public String getPerformance() { return performance;}

    public void setTime(String temps)       { this.time        = temps;}
    public void setScore(String score)      { this.score       = score;}
    public void setMedals(String medailles) { this.medals      = medailles;}
    public void setPlace(int place)         { this.place       = place;}
    public void setPerformance(String perf) { this.performance = perf;} 

    public void saveResultat() throws IOException {
        String csvFilePath = "JavaProject/Resultat.csv";
        try(BufferedWriter bufferWriter = new BufferedWriter(new FileWriter(csvFilePath, true))) {
            bufferWriter.write(getTime() + "," + getScore() +"," +getMedals()+","+getPlace()+","+getPerformance()+"\n");
            System.out.println("Les informations ont bien été enregistrés");
        } catch( IOException e) {
            System.err.println("Erreur lors de l'enregistrement des informations de l'utilisateur dans le fichier CSV: " + e.getMessage());
        }
    }
}
