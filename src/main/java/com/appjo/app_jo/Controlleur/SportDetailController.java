package com.appjo.app_jo.Controlleur;

import com.appjo.app_jo.Modele.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SportDetailController {

    @FXML
    private Text nomSport;
    @FXML
    private Text text1;
    @FXML
    private Text tire1;
    @FXML
    private Text text2;
    @FXML
    private Text titre2;
    @FXML
    private Text text3;

    public void loadSportDetails(int sportId) {
        String query = "SELECT nom, text1, tire1, text2, titre2, text3 FROM Sport WHERE id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, sportId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nomSport.setText(resultSet.getString("nom"));
                text1.setText(resultSet.getString("text1"));
                tire1.setText(resultSet.getString("tire1"));
                text2.setText(resultSet.getString("text2"));
                titre2.setText(resultSet.getString("titre2"));
                text3.setText(resultSet.getString("text3"));
            }
        } catch (SQLException | IOException e) {
        }
    }
}
