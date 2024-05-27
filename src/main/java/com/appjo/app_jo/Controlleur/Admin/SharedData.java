package com.appjo.app_jo.Controlleur.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SharedData {
    // Athlète
    private static ObservableList<ObservableList<String>> athleteData = FXCollections.observableArrayList();

    public static ObservableList<ObservableList<String>> getAthleteData() {
        return athleteData;
    }

    public static void setAthleteData(ObservableList<ObservableList<String>> data) {
        athleteData = data;
    }

    // Évènement
    private static ObservableList<ObservableList<String>> eventData = FXCollections.observableArrayList();

    public static ObservableList<ObservableList<String>> getEventData() {
        return eventData;
    }

    public static void setEventData(ObservableList<ObservableList<String>> data1) {
        eventData = data1;
    }


    public static void addSportData(ObservableList<String> newSportData) {
    }

    public static ObservableList<ObservableList<String>> getSportData() {
        return athleteData;
    }
}
