package com.medeasy.controllers.patient;

import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

public class PatientProfileController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField fatherName;

    @FXML
    private TextField motherName;

    @FXML
    private DatePicker dob;
    @FXML
    private Rectangle img;
    @FXML
    private TextField email;

    @FXML
    private TextField address;

    @FXML
    private TextField username;

    @FXML
    private TextField bloodGroup;

    @FXML
    private TextField height;

    @FXML
    private TextField weight;

    @FXML
    private TextField lastAppointment;

    @FXML
    private TextField currentDisease;
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            try {
                DatabaseConnection databaseConnection =new DatabaseConnection();
                Patient patient = databaseConnection.getPatient(userID,"userID");
                name.setText(patient.getPersonNameBn());
                fatherName.setText(patient.getFatherNameBn());
                motherName.setText(patient.getMotherNameBn());
                dob.setValue(LocalDate.parse(patient.getDob(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                bloodGroup.setText(patient.getBloodGroup());
                currentDisease.setText(patient.getDisease());

                username.setText(patient.getUsername());
                String path = "temp/"+patient.getUserID()+".png";
                if(patient.getBlob()!=null) {
                    byte byteArray[] = patient.getBlob().getBytes(1, (int) patient.getBlob().length());
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
                email.setText(patient.getEmail());
                address.setText(patient.getAddressBn());
                height.setText(patient.getHeight());
                weight.setText(patient.getWeight());

                String sql = "SELECT doctorID FROM appointments WHERE userID = ? ORDER BY `appointments`.`time` DESC";
                Connection connection = databaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1,userID);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next())
                {
                    Doctor doctor = databaseConnection.getDoctor(resultSet.getString(1),"userID");
                    lastAppointment.setText(doctor.getPersonNameEn());;
                }




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
