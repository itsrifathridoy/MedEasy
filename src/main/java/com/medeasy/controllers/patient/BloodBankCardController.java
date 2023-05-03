package com.medeasy.controllers.patient;

import com.medeasy.models.BloodBank;
import com.medeasy.models.Record;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class BloodBankCardController implements Initializable {
    @FXML
    private Label name;

    @FXML
    private Label phone;

    @FXML
    private Label address;

    private BloodBank bloodBank;

    public void setBloodBank(BloodBank bloodBank) {
        this.bloodBank = bloodBank;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            name.setText(bloodBank.getName());
            phone.setText(bloodBank.getPhone());
            address.setText(bloodBank.getAddress());

        });
    }
}
