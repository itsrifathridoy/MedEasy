package com.medeasy.controllers.doctor;

import com.medeasy.models.Appointment;
import com.medeasy.models.Doctor;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class DoctorHomeController implements Initializable {


    @FXML
    private Label date;

    @FXML
    private Label time;

    @FXML
    private Label name;

    @FXML
    private Label patients;

    @FXML
    private Rectangle img;

    @FXML
    private VBox appointmentContainer;
    private ArrayList<Appointment> appointments;

    @FXML
    void chatbox(ActionEvent event) {

    }

    @FXML
    void dashboard(ActionEvent event) {

    }

    @FXML
    void doctors(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void patients(ActionEvent event) {

    }

    @FXML
    void profile(ActionEvent event) {

    }
    private String doctorID;

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    private ArrayList<Appointment> getAppointmentList()
    {
        ArrayList<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctorID = '"+doctorID+"'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next())
            {
                appointments.add(new Appointment(resultSet.getString(1),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5)));
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
            setDoctorProfileData();
            for(Appointment appointment:getAppointmentList())
            {
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/appointmentCard.fxml"));
                    Parent root = loader.load();
                    AppointmentCardController controller = loader.getController();
                    controller.setAppointment(appointment);


                    appointmentContainer.getChildren().add(root);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
    void setDoctorProfileData()
    {
        String sql = "SELECT * FROM doctors WHERE userID = '"+doctorID+"'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Doctor doctor = databaseConnection.getDoctor(doctorID,"userID");
            System.out.println(doctorID);
            name.setText(doctor.getPersonNameEn());
            patients.setText("You have "+doctor.getAppointments()+" appointments remaining");
            DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
            String timeString = timeFormat.format(new Date()).toString();
            time.setText(timeString);
            DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
            String dateString = dateFormat.format(new Date()).toString();
            date.setText(dateString);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void search(KeyEvent event) {
        for(Appointment appointment:appointments)
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/appointmentCard.fxml"));
                Parent root = loader.load();
                AppointmentCardController controller = loader.getController();
                controller.setAppointment(appointment);


                appointmentContainer.getChildren().add(root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
