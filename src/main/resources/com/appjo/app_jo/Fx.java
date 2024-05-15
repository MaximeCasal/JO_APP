package SceneBuilderCss;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Fx extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Button menuButton = new Button("Menu");
        
        // Créer le contenu du menu
        VBox menuContent = new VBox();
        menuContent.getChildren().addAll(
            new Button("Option 1"),
            new Button("Option 2"),
            new Button("Option 3")
        );
        menuContent.setAlignment(Pos.CENTER);
        
        // Créer un StackPane pour contenir le contenu du menu et le bouton du menu
        StackPane root = new StackPane();
        root.getChildren().addAll(menuContent, menuButton);
        
        // Aligner le contenu du menu sur le côté gauche
        StackPane.setAlignment(menuContent, Pos.CENTER_LEFT);
        
        // Associez une action au bouton du menu pour afficher ou masquer le contenu du menu
        menuButton.setOnAction(event -> {
            if (menuContent.isVisible()) {
                menuContent.setVisible(false);
            } else {
                menuContent.setVisible(true);
            }
        });
        
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
