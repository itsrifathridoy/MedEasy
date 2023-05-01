package com.medeasy.controllers.admin;

import com.medeasy.models.Doctor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorCardController implements Initializable {
    private Doctor doctor;
    @FXML
    private Circle picture;

    @FXML
    private Label name;

    @FXML
    private Label qualification;

    @FXML
    private Label designation;

    @FXML
    private Label specialities;

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    name.setText(doctor.getPersonNameEn());
                    qualification.setText(doctor.getQualification());
                    designation.setText(doctor.getDesignation());
                    specialities.setText(doctor.getSpecialities());
                    if (doctor.getBlob() != null) {
                        String path = "temp/" + doctor.getUserID() + ".png";
                        byte byteArray[] = doctor.getBlob().getBytes(1, (int) doctor.getBlob().length());
                        FileOutputStream outPutStream = new FileOutputStream(path);
                        outPutStream.write(byteArray);
                        outPutStream.close();
                        FileInputStream imgStream = new FileInputStream(path);
                        picture.setFill(new ImagePattern(new Image(imgStream)));
                    } else {
                        FileInputStream imgStream = new FileInputStream("src/main/resources/com/medeasy/img/img_5.png");
                        picture.setFill(new ImagePattern(new Image(imgStream)));
                    }
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

    }
}
