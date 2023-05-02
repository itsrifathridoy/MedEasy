package com.medeasy.controllers.patient;

import com.medeasy.chatsocket.chat.controller.ClientFormController;
import com.medeasy.controllers.admin.AdminHomeController;
import com.medeasy.controllers.admin.DoctorCardController;
import com.medeasy.controllers.admin.PatientCardController;
import com.medeasy.controllers.doctor.DoctorHomeController;
import com.medeasy.models.Appointment;
import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.util.*;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PatientHomeController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label grettings;

    @FXML
    private Rectangle img;

    @FXML
    private Label name;

    @FXML
    private Label age;

    @FXML
    private Label bloodGroup;

    @FXML
    private Label height;

    @FXML
    private Label weight;

    @FXML
    private Label appointDoctor;

    @FXML
    private Label appointHospital;

    @FXML
    private Label appointTime;

    @FXML
    private Label medName;

    @FXML
    private Label Medtime;
    private String userID;
    private ArrayList<Patient> patientList;

    @FXML
    void chatbox(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/chatBox/chatBox.fxml"));

            Parent root = loader.load();
            ClientFormController controller = loader.getController();
            System.out.println(controller);
            System.out.println(getClass().getSimpleName()+userID);
            controller.setUsername(userID);
            rootPane.setCenter(root);
            rootPane.setRight(null);
            rootPane.setTop(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @FXML
    void dashboard(ActionEvent event) {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/patients/patientHome.fxml");
        Scene scene = new Scene(fxmlScene.getRoot());
        PatientHomeController controller = (PatientHomeController) fxmlScene.getController();
        controller.setUserID(userID);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void findBlood(ActionEvent event) {

    }
    @FXML
    void records(ActionEvent event) {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/patients/records.fxml");
        rootPane.setCenter(fxmlScene.getRoot());
        rootPane.setRight(null);
    }

    @FXML
    void doctors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/patients/doctorsList.fxml"));
            Parent root = loader.load();
            rootPane.setCenter(root);
            rootPane.setRight(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void logout(ActionEvent event) {
        LoginInfoSave.clearLoginInfo();
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/loginReg/login.fxml");
        FXMLScene.switchScene(fxmlScene, (Node) event.getSource());
    }

    @FXML
    void profile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/medeasy/views/patients/profile.fxml"));
        Parent root = loader.load();
        ProfileController controller = loader.getController();
        controller.setUserID(userID);
        rootPane.setCenter(root);
        rootPane.setRight(null);
    }


    private ArrayList<Appointment> getAppointmentList() {
        ArrayList<Appointment>appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE userID = '" + userID + "'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next()) {
                appointments.add(new Appointment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5),resultSet.getString(6),resultSet.getString(7),resultSet.getString(8)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(appointments.size());
        return appointments;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            try {
                DatabaseConnection databaseConnection = new DatabaseConnection();
                Patient patient =databaseConnection.getPatient(userID,"userID");
                name.setText(patient.getUsername());
                grettings.setText(new GreetingMaker(LocalTime.now()).printTimeOfDay());
                DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
                String timeString = timeFormat.format(new Date()).toString();
                time.setText(timeString);
                DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
                String dateString = dateFormat.format(new Date()).toString();
                date.setText(dateString);
                grettings.setText(new GreetingMaker(LocalTime.now()).printTimeOfDay());
                if(patient.getBlob()!=null) {
                    String path = "temp/"+patient.getUserID()+".png";
                    byte byteArray[] = patient.getBlob().getBytes(1, (int) patient.getBlob().length());
                    FileOutputStream outPutStream = new FileOutputStream(path);
                    outPutStream.write(byteArray);
                    outPutStream.close();
                    FileInputStream imgStream = new FileInputStream(path);
                    img.setFill(new ImagePattern(new Image(imgStream)));
                }

//                LocalDate birthDate = LocalDate.parse(patient.getDob(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                LocalDate currentDate = LocalDate.now();
//                String ageStr =((Period.between(birthDate,currentDate).getYears()>0)?(Period.between(birthDate,currentDate).getYears()+" Years "):("")) + ((Period.between(birthDate,currentDate).getMonths()>0)?(Period.between(birthDate,currentDate).getMonths()+ " Months "):(""))+ ((Period.between(birthDate,currentDate).getDays()>0)?(Period.between(birthDate,currentDate).getDays()+" Days"):(""));
//                age.setText(ageStr);
                height.setText(patient.getHeight());
                weight.setText(patient.getWeight());


                String sql = "SELECT time,doctorID FROM appointments WHERE appointmentID = '"+patient.getLastAppointment()+"'";
                ResultSet resultSet = databaseConnection.queryData(sql);
                String doctorID = null;
                String time = null;
                if(resultSet.next())
                {
                    time = resultSet.getString(1);
                    doctorID =resultSet.getString(2);
                }
                appointTime.setText(time);
                Doctor doctor = databaseConnection.getDoctor(doctorID,"userID");
                appointDoctor.setText(doctor.getPersonNameEn());
                appointHospital.setText(doctor.getHospital());
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
