package com.appjo.app_jo.Controlleur;

import java.time.LocalDate;

public class Competitions {
    private LocalDate date;
    private String event;
    private String location;

    public Competitions(LocalDate date, String event, String location) {
        this.date = date;
        this.event = event;
        this.location = location;
    }

    // Getters
    public LocalDate getDate() {
        return date;
    }

    public String getEvent() {
        return event;
    }

    public String getLocation() {
        return location;
    }
}
