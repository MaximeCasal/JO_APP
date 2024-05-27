module com.joapp.jo_app {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires javafx.graphics;
    requires java.sql;

    opens com.appjo.app_jo to javafx.fxml;
    exports com.appjo.app_jo;
    exports com.appjo.app_jo.Controlleur;
    opens com.appjo.app_jo.Controlleur;
    exports com.appjo.app_jo.Controlleur.Admin;
    opens com.appjo.app_jo.Controlleur.Admin;
}