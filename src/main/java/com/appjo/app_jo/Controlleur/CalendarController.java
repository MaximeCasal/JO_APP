package com.appjo.app_jo.Controlleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class CalendarController {

    @FXML
    private Text monthLabel;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private ListView<String> dailyScheduleList;

    @FXML
    private ListView<String> notificationList;

    @FXML
    private TextField notificationInput;

    @FXML
    private Button btn24;
    @FXML
    private Button btn25;
    @FXML
    private Button btn26;
    @FXML
    private Button btn27;
    @FXML
    private Button btn28;
    @FXML
    private Button btn29;
    @FXML
    private Button btn30;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Button btn9;
    @FXML
    private Button btn10;
    @FXML
    private Button btn11;

    @FXML
    public void initialize() {
        btn24.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 24)));
        btn25.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 25)));
        btn26.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 26)));
        btn27.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 27)));
        btn28.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 28)));
        btn29.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 29)));
        btn30.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 7, 30)));
        btn1.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 1)));
        btn2.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 2)));
        btn3.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 3)));
        btn4.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 4)));
        btn5.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 5)));
        btn6.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 6)));
        btn7.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 7)));
        btn8.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 8)));
        btn9.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 9)));
        btn10.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 10)));
        btn11.setOnAction(event -> handleDateButtonClick(LocalDate.of(2024, 8, 11)));

        // Initialize the calendar and other elements
        initializeCalendar();
    }

    private void initializeCalendar() {
        // Populate the calendar with initial values, etc.
        loadEventsFromDatabase();
    }

    private void loadEventsFromDatabase() {
        String url = "jdbc:mysql://localhost:3306/jo_app";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT event_date, event_time, event_name FROM calendar_events")) {

            List<String> events = new ArrayList<>();
            while (rs.next()) {
                LocalDate date = rs.getDate("event_date").toLocalDate();
                String time = rs.getTime("event_time").toString();
                String name = rs.getString("event_name");
                events.add(date.format(DateTimeFormatter.ofPattern("dd MMM yyyy")) + " " + time + " - " + name);
            }

            dailyScheduleList.getItems().addAll(events);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleDateButtonClick(LocalDate date) {
        Event event = getEventByDate(date);
        if (event != null) {
            openEventDetailPage(event);
        } else {
            System.out.println("No event found for this date.");
        }
    }

    private Event getEventByDate(LocalDate date) {
        String url = "jdbc:mysql://localhost:3306/jo_app";
        String user = "root";
        String password = "";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM calendar_events WHERE event_date = ?")) {

            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Event(
                        rs.getInt("id"),
                        rs.getDate("event_date").toLocalDate(),
                        rs.getTime("event_time").toLocalTime(),
                        rs.getString("event_name"),
                        rs.getString("event_description")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void openEventDetailPage(Event event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/appjo/app_jo/EventDetail.fxml"));
            AnchorPane page = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Event Details");
            stage.setScene(new Scene(page));

            EventDetailController controller = loader.getController();
            controller.setDialogStage(stage);
            controller.setEventDetails(event);

            stage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addNotification() {
        String notification = notificationInput.getText();
        if (notification != null && !notification.isEmpty()) {
            notificationList.getItems().add(notification);
            notificationInput.clear();
        }
    }

    public void sportPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Sport/Sport.fxml");
    }

    public void evenementPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/EventDetail.fxml");
    }

    public void calendrierPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/CalendarView.fxml");
    }

    public void accueilPage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/PrimaryScene.fxml");
    }

    public void athletePage(MouseEvent mouseEvent) {
        loadScene(mouseEvent, "/com/appjo/app_jo/Sport/AthletesScene.fxml");
    }

    private void loadScene(MouseEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
