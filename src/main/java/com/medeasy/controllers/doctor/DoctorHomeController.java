package com.medeasy.controllers.doctor;

import com.medeasy.chatsocket.chat.controller.ClientFormController;
import com.medeasy.controllers.admin.AdminHomeController;
import com.medeasy.controllers.admin.DoctorCardController;
import com.medeasy.controllers.admin.DoctorListController;
import com.medeasy.controllers.admin.PatientCardController;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class DoctorHomeController implements Initializable {

    @FXML
    private BorderPane rootPane;

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
    private Label grettings;

    @FXML
    private VBox appointmentContainer;
    @FXML
    private TextField searchBox;
    private ArrayList<Appointment> appointments;
    private ArrayList<Patient> patientList;
    private String userID;
    private Doctor doctor;

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @FXML
    void chatbox(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/chatbox.fxml"));

            Parent root = loader.load();
            ClientFormController controller = loader.getController();
            System.out.println(controller);
            controller.setUsername(doctor.getPersonNameEn());
            rootPane.setCenter(root);
            rootPane.setRight(null);
            rootPane.setTop(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void dashboard(ActionEvent event) {
        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/doctors/doctorHome.fxml");
        Scene scene = new Scene(fxmlScene.getRoot());
        DoctorHomeController controller = (DoctorHomeController) fxmlScene.getController();
        controller.setDoctorID(doctorID);
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void doctors(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/patients/doctorsList.fxml"));
            Parent root = loader.load();
            DoctorListController controller = loader.getController();
            controller.setUserID(doctorID);
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
    void patients(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/patients/patientsList.fxml"));

            Parent root = loader.load();
            PatientsListController controller = loader.getController();
            controller.setDoctorID(doctorID);
            rootPane.setCenter(root);
            rootPane.setRight(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void profile(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/profile.fxml"));

        Parent root = loader.load();
        DoctorProfileController controller = loader.getController();
        controller.setUserID(doctorID);
        rootPane.setCenter(root);
        rootPane.setRight(null);
    }

    private String doctorID;

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    private ArrayList<Appointment> getAppointmentList() {
        appointments = new ArrayList<>();
        String sql = "SELECT * FROM appointments WHERE doctorID = '" + doctorID + "'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            ResultSet resultSet = databaseConnection.queryData(sql);
            while (resultSet.next()) {
                appointments.add(new Appointment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
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
        Platform.runLater(() -> {
            setDoctorProfileData();
            if(searchBox.getText().isEmpty())
            {
                for (Appointment appointment : getAppointmentList()) {
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
        });

    }

    void setDoctorProfileData() {
        String sql = "SELECT * FROM doctors WHERE userID = '" + doctorID + "'";
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            doctor = databaseConnection.getDoctor(doctorID, "userID");
            System.out.println(doctorID);
            name.setText(doctor.getPersonNameEn());
            patients.setText("You have " + getAppointmentList().size() + " appointments remaining");
            DateFormat timeFormat = new SimpleDateFormat("hh.mm aa");
            String timeString = timeFormat.format(new Date()).toString();
            time.setText(timeString);
            DateFormat dateFormat = new SimpleDateFormat("E, dd MMM yyyy");
            String dateString = dateFormat.format(new Date()).toString();
            date.setText(dateString);
            grettings.setText(new GreetingMaker(LocalTime.now()).printTimeOfDay());

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }



    @FXML
    void search(KeyEvent event) throws SQLException, ClassNotFoundException, IOException {

        appointmentContainer.getChildren().remove(2,appointmentContainer.getChildren().size());


        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(appointmentContainer);
        fadeTransition.setDuration(Duration.seconds(0.5));
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
        String searchSQL = "SELECT * FROM appointments where (name LIKE ? AND doctorID = ?)";
        HashMap<Integer,Object> searchHash = new HashMap<>();
        searchHash.put(1,"%"+((TextField)event.getSource()).getText()+"%");
        searchHash.put(2,doctorID);


        DatabaseReadCall databaseReadCall = new DatabaseReadCall(searchSQL,searchHash);
        databaseReadCall.setOnSucceeded(workerStateEvent -> {
            try {
                ResultSet resultSet = databaseReadCall.getValue();
                while (resultSet.next()) {
                    appointmentArrayList.add(new Appointment(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)));
                    }

                if (appointmentArrayList.size() > 0) {
                    for (Appointment appointment:appointmentArrayList) {
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

                } else {
//                    FXMLLoader loader = new FXMLLoader();
//                    loader.setLocation(getClass().getResource("/com/medeasy/views/others/noResult.fxml"));
//                    Parent root = loader.load();
//                    appointmentContainer.getChildren().add(root);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        });
        Thread thread =new Thread(databaseReadCall);
        thread.setDaemon(true);
        thread.start();
    }
    @FXML
    void appointments(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/appointmentTableList.fxml"));
            Parent root = loader.load();
            AppointmentTableList controller = loader.getController();
            controller.setUserID(doctorID);
            rootPane.setCenter(root);
            rootPane.setRight(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
