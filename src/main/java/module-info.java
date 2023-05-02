module com.medeasy.medeasy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.mail;
    requires com.jfoenix;
    requires MaterialFX;
    requires io.github.cdimascio.dotenv.java;
    requires twilio;
    requires mysql.connector.j;
    requires java.sql;
    requires java.prefs;
    requires org.controlsfx.controls;
    requires fontawesomefx;

    opens com.medeasy.controllers to javafx.fxml;
    exports com.medeasy.controllers;
    exports com.medeasy;
    opens com.medeasy to javafx.fxml;
    exports com.medeasy.models;
    opens com.medeasy.models to javafx.fxml;
    exports com.medeasy.util;
    opens com.medeasy.util to javafx.fxml;
    opens com.medeasy.controllers.admin to javafx.fxml;
    exports com.medeasy.controllers.admin;
    opens com.medeasy.controllers.doctor to javafx.fxml;
    exports com.medeasy.controllers.doctor;
    opens com.medeasy.controllers.patient to javafx.fxml;
    exports com.medeasy.controllers.patient;
    opens com.medeasy.controllers.loginReg to javafx.fxml;
    exports com.medeasy.controllers.loginReg;
    opens com.medeasy.controllers.chat to javafx.fxml;
    exports com.medeasy.controllers.chat;

}
