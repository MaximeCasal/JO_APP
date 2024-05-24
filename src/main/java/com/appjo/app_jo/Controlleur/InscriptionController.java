    package com.appjo.app_jo.Controlleur;

    import javafx.animation.TranslateTransition;
    import javafx.fxml.FXML;
    import javafx.fxml.Initializable;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.control.TextField;
    import javafx.stage.Screen;
    import javafx.util.Duration;
    import javafx.scene.layout.AnchorPane;

    import javafx.scene.input.MouseEvent;
    import java.net.URL;
    import java.util.ResourceBundle;

    import static javafx.scene.layout.AnchorPane.*;

    public class InscriptionController implements Initializable {

        @FXML
        private AnchorPane layerssignup;
        @FXML
        private AnchorPane Layer2;
        @FXML
        private Button signin;
        @FXML
        private Label l1;
        @FXML
        private Label l2;
        @FXML
        private Label l3;
        @FXML
        private Label s1;
        @FXML
        private Label s2;
        @FXML
        private Label s3;
        @FXML
        private Button signup;
        @FXML
        private Label a2;
        @FXML
        private Label b2;
        @FXML
        private Label a1;
        @FXML
        private Label a4;
        @FXML
        private Label b1;
        @FXML
        private Button btnsignup;
        @FXML
        private Button btnsignin;
        @FXML
        private TextField u1;
        @FXML
        private TextField u2;
        @FXML
        private TextField u3;
        @FXML
        private TextField n1;
        @FXML
        private TextField n2;
        @FXML
        private Label n3;
        @FXML
        private AnchorPane Layer1;

        private double layer1StartX;
        private double layer2StartX;

        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

            // Ajustement des marges des couches
            layer1StartX = Layer1.getTranslateX();
            layer2StartX = Layer2.getTranslateX();


            a1.setVisible(true);
            a2.setVisible(true);
            a4.setVisible(true);
            s3.setVisible(false);
            signup.setVisible(false);
            b1.setVisible(true);
            b2.setVisible(false);
            btnsignin.setVisible(false);
            n1.setVisible(false);
            n2.setVisible(false);
            n3.setVisible(false);
            u1.setVisible(true);
            u2.setVisible(true);
            u3.setVisible(true);
            s1.setVisible(false);
            l1.setVisible(false);
            l3.setVisible(false);
        }

        @FXML
        private void btn(MouseEvent event) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(Layer2);

            slide.setToX(-0.1 * Screen.getPrimary().getBounds().getWidth());
            slide.play();

            Layer1.setTranslateX(50);
            btnsignin.setVisible(true);
            b1.setVisible(false);
            b2.setVisible(true);

            s1.setVisible(true);
            s2.setVisible(true);
            s3.setVisible(true);
            signup.setVisible(true);
            l1.setVisible(true);
            l2.setVisible(false);
            l3.setVisible(true);
            signin.setVisible(false);
            a1.setVisible(false);
            a2.setVisible(true);
            btnsignup.setVisible(false);
            n1.setVisible(true);
            n2.setVisible(true);
            n3.setVisible(true);
            u1.setVisible(false);
            u2.setVisible(false);
            u3.setVisible(false);

            slide.setOnFinished(e-> {});
        }

        @FXML
        private void btn2(MouseEvent event) {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.7));
            slide.setNode(Layer2);

            slide.setToX(0.00000000000001*Screen.getPrimary().getBounds().getWidth());
            slide.play();

            Layer1.setTranslateX(0);
            btnsignin.setVisible(false);
            b1.setVisible(true);
            b2.setVisible(false);

            s1.setVisible(false);
            s2.setVisible(false);
            s3.setVisible(false);
            signup.setVisible(false);
            l1.setVisible(false);
            l2.setVisible(true);
            l3.setVisible(false);
            signin.setVisible(true);
            a1.setVisible(true);
            a2.setVisible(true);
            btnsignup.setVisible(true);
            n1.setVisible(false);
            n2.setVisible(false);
            n3.setVisible(false);
            u1.setVisible(true);
            u2.setVisible(true);
            u3.setVisible(true);

            slide.setOnFinished(e-> {});
        }

        @FXML
        private void signin(MouseEvent event) {

        }

        @FXML
        private void btnsignup(MouseEvent event) {

        }
    }
