package com.appjo.app_jo.Controlleur;

import java.time.LocalDate;
import java.time.LocalTime;

public class Event {
    private int id;
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String description;

    public Event(int id, LocalDate date, LocalTime time, String name, String description) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.name = name;
        this.description = description;
    }

    public int getId() { return id; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public String getName() { return name; }
    public String getDescription() { return description; }
}

