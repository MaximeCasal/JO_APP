package com.appjo.app_jo.Controlleur;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompetitionsModel {
    private List<Competitions> competitions;

    public CompetitionsModel() {
        this.competitions = new ArrayList<>();
        // Exemple de données
        competitions.add(new Competitions(LocalDate.of(2024, 7, 28), "100m hommes", "Stade Olympique"));
        competitions.add(new Competitions(LocalDate.of(2024, 7, 29), "200m femmes", "Stade Olympique"));
    }

    public List<Competitions> getCompetitionsByDate(LocalDate date) {
        return competitions.stream()
                .filter(competition -> competition.getDate().equals(date))
                .collect(Collectors.toList());
    }
}

