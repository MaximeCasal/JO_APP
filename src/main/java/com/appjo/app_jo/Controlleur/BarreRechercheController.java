package com.appjo.app_jo.Controlleur;

import com.appjo.app_jo.Modele.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BarreRechercheController {

    public static boolean rechercherEtAfficherResultats(String motCle) {
        boolean resultatTrouve = false;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String[] tables = {"Athlete", "Evenement", "Partenaire", "Site", "Sport", "Utilisateur"};
            for (String table : tables) {
                String sql = "SELECT nom FROM " + table + " WHERE nom LIKE ?";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, "%" + motCle + "%");
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        resultatTrouve = true;
                        return resultatTrouve;
                    }
                }
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return resultatTrouve;
    }
}