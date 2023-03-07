module com.medeasy.medeasy {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.mail;
    requires com.jfoenix;
    requires MaterialFX;
    requires io.github.cdimascio.dotenv.java;

    opens com.medeasy.loginReg to javafx.fxml;
    exports com.medeasy.loginReg;
    exports com.medeasy;
    opens com.medeasy to javafx.fxml;
    exports com.medeasy.users;
    opens com.medeasy.users to javafx.fxml;
}