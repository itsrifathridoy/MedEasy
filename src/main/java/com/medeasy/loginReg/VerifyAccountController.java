package com.medeasy.loginReg;

import com.medeasy.users.Patient;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class VerifyAccountController {

    public TextField password;
    public TextField confirmPassword;
    private Patient patient;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public Pane contentArea;

    public void setData(Patient patient, Pane contentArea, ImageView img1, ImageView img2, ImageView img3)
    {
        this.patient=patient;
        this.contentArea = contentArea;
        this.img1 =img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public void createAccount(ActionEvent actionEvent) {
        System.out.println(patient);
        System.out.println(patient.getbId());
    }
}
