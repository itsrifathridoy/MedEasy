package com.medeasy.loginReg;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsViewController implements Initializable {
    public Label name;
    public Label dob;
    public Label fName;
    public Label mNName;
    public Label address;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(RegBirthController.patient.getPatientName());
        dob.setText(RegBirthController.patient.getDob());
        fName.setText(RegBirthController.patient.getFatherNameBn());
        mNName.setText(RegBirthController.patient.getMotherNameBn());
        address.setText(RegBirthController.patient.getAddressBn());
    }

    public void nextCreateAccount(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("emailPassword.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
