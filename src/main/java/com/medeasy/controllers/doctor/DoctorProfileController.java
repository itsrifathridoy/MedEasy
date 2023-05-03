package com.medeasy.controllers.doctor;

import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DoctorProfileController implements Initializable {

    @FXML
    private Rectangle img;

    @FXML
    private TextField name;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private DatePicker dob;

    @FXML
    private TextField email;

    @FXML
    private TextField mobile;

    @FXML
    private TextField qualification;

    @FXML
    private TextField designation;

    @FXML
    private TextField speciality;

    @FXML
    private TextField numOfOperation;

    @FXML
    private TextField hospital;

    @FXML
    private TextField hospitalAddress;
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            try {
                List<String> list  = new ArrayList<>();
                list.add("Male");
                list.add("Female");
                ObservableList<String> items = FXCollections.observableArrayList(list);
                gender.setItems(items);
                DatabaseConnection databaseConnection =new DatabaseConnection();
                Doctor doctor = databaseConnection.getDoctor(userID,"userID");
                name.setText(doctor.getPersonNameEn());
                email.setText(doctor.getEmail());
                dob.setValue(LocalDate.parse(doctor.getDob(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                mobile.setText(doctor.getMobile());
                qualification.setText(doctor.getQualification());

                designation.setText(doctor.getDesignation());
                String path = "temp/"+doctor.getUserID()+".png";
                if(doctor.getBlob()!=null) {
                    byte byteArray[] = doctor.getBlob().getBytes(1, (int) doctor.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }
                else
                {
                    FileInputStream imgStream = new FileInputStream("src/main/resources/com/medeasy/img/img_5.png");
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }
                email.setText(doctor.getEmail());
                hospital.setText(doctor.getHospital());
                hospitalAddress.setText(doctor.getHospitalAddress());
                speciality.setText(doctor.getSpecialities());
                numOfOperation.setText(doctor.getNumOfOperations());




            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
}
