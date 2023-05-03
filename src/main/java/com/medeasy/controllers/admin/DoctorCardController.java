package com.medeasy.controllers.admin;

import com.medeasy.models.Doctor;
import com.medeasy.models.Patient;
import com.medeasy.models.User;
import com.medeasy.util.DatabaseConnection;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;

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
    private String userID;

    public void setUserID(String userID) {
        this.userID = userID;
    }

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
    private String generateUserID(String role) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Dhaka"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        UUID uuid = UUID.randomUUID();
        String userID = role + "_" + timestamp + uuid.toString().substring(0, 6);
        return userID;
    }
    @FXML
    void bookAppointment(ActionEvent event) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();
        User user = databaseConnection.getUser(userID, "userID");

        String query = "SELECT * FROM `users` WHERE" + "`" + "userID" + "` LIKE '" + userID + "'";

        Statement statement1 = connection.createStatement();

        ResultSet userResult = statement1.executeQuery(query);
        String role = null;
        if (userResult.next()) {
            role = userResult.getString(4);
        }
        System.out.println(role);
        if (role.equals("PATIENT")) {
            Patient patient = databaseConnection.getPatient(userID, "userID");
            String statusQuery = "SELECT COUNT(1) FROM appointments WHERE userID = '" + userID + "' AND doctorID = '" + doctor.getUserID() + "'";
            PreparedStatement statement = connection.prepareStatement(statusQuery);
            ResultSet resultSet = statement.executeQuery(statusQuery);
            String status = null;
            if (resultSet.next()) {
                if (resultSet.getInt(1) == 0) {
                    status = "NEW PATIENT";
                } else {
                    status = "OLD PATIENT";
                }
            }
            TextInputDialog textInputDialog = new TextInputDialog();
            textInputDialog.setTitle("Please Provide a description");
            Optional<String> description = textInputDialog.showAndWait();
            String des = null;
            if (description.isPresent()) {
                des = description.get();
            }
            String sql = "INSERT INTO `appointments` (`appointmentID`, `userID`, `doctorID`, `discription`, `status`, `currentStage`, `name`, `time`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement2 = connection.prepareStatement(sql);

            String appointmentID = generateUserID("APPOINTMENT");
            LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Dhaka"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
            String timestamp = now.format(formatter);


            statement2.setString(1, appointmentID);
            System.out.println(userID);
            statement2.setString(2, userID);
            statement2.setString(3, doctor.getUserID());
            statement2.setString(4, des);
            statement2.setString(5, "Pending");
            statement2.setString(6, status);
            statement2.setString(7, patient.getUsername());
            statement2.setString(8, timestamp);
            int resultSet1 = statement2.executeUpdate();
            if (resultSet1 > 0) {
                Notifications notifications = Notifications.create()
                        .title("An appointment has been booked")
                        .text("An appointment has been booked to " + doctor.getPersonNameEn())
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT);
                notifications.show();
            }

        }
    }

}
