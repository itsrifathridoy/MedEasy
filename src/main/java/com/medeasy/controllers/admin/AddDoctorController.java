package com.medeasy.controllers.admin;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.medeasy.models.Doctor;
import com.medeasy.models.EmailTemplate;
import com.medeasy.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddDoctorController implements Initializable {

    @FXML
    private BorderPane rootPane;

    @FXML
    private JFXTextField doctorName;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXComboBox<String> gender;

    @FXML
    private DatePicker dob;

    @FXML
    private JFXTextField hospital;

    @FXML
    private JFXTextField hospitalAddress;

    @FXML
    private JFXTextField speciality;

    @FXML
    private JFXTextField numOfOperations;

    @FXML
    private JFXTextField numOfAppointments;

    @FXML
    private JFXTextField qualification;

    @FXML
    private JFXTextField designation;

    @FXML
    private JFXTextField mobile;
    private Doctor doctor;


    boolean validate(Control control)
    {
        boolean isValidate= false;
        if(control instanceof TextField)
        {
            TextField textField = (TextField) control;
            if(textField.getText().isEmpty() || textField.getText().length()<3)
            {
                Notifications notifications = Notifications.create()
                        .title(textField.getPromptText() + " should not be empty or not less then 3 character")
                        .text("Please fill " + textField.getPromptText() + " with valid information")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT);
                 notifications.showWarning();
            }
            else
            {
                isValidate=true;
            }
        }
        else if(control instanceof ComboBox) {
            ComboBox<String> comboBox = (ComboBox) control;
            if (comboBox.getSelectionModel().getSelectedItem().isEmpty()) {
                Notifications notifications = Notifications.create()
                        .title(comboBox.getPromptText() + " is not selected")
                        .text("Please select " + comboBox.getPromptText() + " from comboBox")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT);
                notifications.showWarning();
            }
            else
            {
                isValidate=true;
            }
        }
        return isValidate;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<String> list  = new ArrayList<>();
        list.add("Male");
        list.add("Female");
        ObservableList<String> items = FXCollections.observableArrayList(list);
        gender.setItems(items);
    }

    @FXML
    void addDoctor(ActionEvent event) {

    }

    @FXML
    void doctors(ActionEvent event) {

    }

    private String generatePassword()
    {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0, 8);
    }

    @FXML
    void save(ActionEvent event) throws SQLException, ClassNotFoundException {

        if(validate(doctorName) && validate(email) && validate(hospital) && validate(speciality) && validate(gender) && validate(qualification) && validate(doctorName) && validate(designation) && validate(mobile))
        {
            if(isValidEmail(email.getText()))
            {

                String checkUserAvailabilitySQL = "SELECT COUNT(1) FROM users WHERE email = ?";
                HashMap<Integer,Object> queries = new HashMap<>();
                queries.put(1,email.getText());
                DatabaseReadCall databaseReadCall = new DatabaseReadCall(checkUserAvailabilitySQL,queries);
                databaseReadCall.setOnSucceeded(wse -> {
                    ResultSet resultSet = databaseReadCall.getValue();
                    int count = 0;
                    try {
                        if (resultSet.next()) {
                            count = resultSet.getInt(1);
                            System.out.println("The count is: " + count);
                        }
                        if (count == 0) {

                            createDoctorAccount();
                            try {
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("/com/medeasy/views/doctors/doctorsList.fxml"));

                                Parent root = loader.load();
                                rootPane.setCenter(root);
                                rootPane.setRight(null);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        else if(count==1)
                        {
                            Notifications notifications = Notifications.create()
                                    .title("A doctor account already available with "+ email.getText())
                                    .text("Please try with another email address.")
                                    .graphic(null)
                                    .hideAfter(Duration.seconds(3))
                                    .position(Pos.TOP_RIGHT);
                            notifications.showError();

                        }

                    }catch (Exception e) {
                        e.printStackTrace();

                    }
                });

                databaseReadCall.setOnFailed(workerStateEvent -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Database Query Failed", ButtonType.OK);
                    alert.setTitle("Error Occurs");
                    alert.setHeaderText("Something went wrong");
                    alert.show();
                });
                new Thread(databaseReadCall).start();
            }
            else
            {
                Notifications notifications = Notifications.create()
                        .title(email.getPromptText() + " is not valid")
                        .text("Ex. yourname@domain.com")
                        .graphic(null)
                        .hideAfter(Duration.seconds(3))
                        .position(Pos.TOP_RIGHT);
                notifications.showError();
            }
        }

    }
    private void createDoctorAccount()
    {
        String userCreateSQL = "INSERT INTO users (userID, email, password, role) VALUES (?, ?, ?, ?)";
        String doctorCreateSQL = "INSERT INTO doctors (userID, personNameEn, gender, dob, email, mobile, specialities, appointments, numOfOperations, qualification, designation, hospital, hospitalAddress) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
        String password = generatePassword();
        try {
            String userId = generateUserID("DOCTOR");
            String encryptedSecret = new Encryption().getEncryptedKey(password);
            HashMap<Integer,Object> userCreateQuery = new HashMap<>();
            userCreateQuery.put(1,userId);
            userCreateQuery.put(2,email.getText());
            userCreateQuery.put(3,encryptedSecret);
            userCreateQuery.put(4,"DOCTOR");
            DatabaseWriter databaseWriter = new DatabaseWriter(userCreateSQL,userCreateQuery);
            databaseWriter.join();
            int userRowInserted = databaseWriter.getRowInserted();

            //Add data on doctors table

            HashMap<Integer,Object> doctorCreateQuery = new HashMap<>();
            doctorCreateQuery.put(1, userId);
            doctorCreateQuery.put(2, doctorName.getText());
            doctorCreateQuery.put(3, gender.getSelectionModel().getSelectedItem());
            System.out.println(dob.getValue());
            doctorCreateQuery.put(4, dob.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            doctorCreateQuery.put(5, email.getText());
            doctorCreateQuery.put(6, mobile.getText());
            doctorCreateQuery.put(7, speciality.getText());
            doctorCreateQuery.put(8, numOfAppointments.getText());
            doctorCreateQuery.put(9, numOfOperations.getText());
            doctorCreateQuery.put(10, qualification.getText());
            doctorCreateQuery.put(11, designation.getText());
            doctorCreateQuery.put(12, hospital.getText());
            doctorCreateQuery.put(13, hospitalAddress.getText());

            DatabaseWriter patientDatabaseWriter = new DatabaseWriter(doctorCreateSQL,doctorCreateQuery);
            patientDatabaseWriter.join();
            System.out.println("Done");
            int doctorRowsInserted = patientDatabaseWriter.getRowInserted();
            System.out.println(userRowInserted+" "+ doctorRowsInserted);
            if (doctorRowsInserted > 0 && userRowInserted>0) {
                System.out.println("Inserted");
                EmailTemplate emailTemplate = new EmailTemplate(doctorName.getText(),password);
                SendEmail sendOtp = new SendEmail(email.getText(), "Welcome to MedEasy", emailTemplate.getDoctorProfileTemplate());
                sendOtp.setOnSucceeded(workerStateEvent -> {

                    Notifications notifications = Notifications.create()
                            .title("A new doctor account has been created")
                            .text("An email has been send to doctor email address with login instructions")
                            .graphic(null)
                            .hideAfter(Duration.seconds(3))
                            .position(Pos.TOP_RIGHT);
                    notifications.showInformation();

                });
                Thread thread = new Thread(sendOtp);
                thread.setDaemon(true);
                thread.start();

            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR,"Error Occurs while creating account");
                alert.show();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    private String generateUserID(String role) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Dhaka"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        UUID uuid = UUID.randomUUID();
        String userID = role + "_" + timestamp + uuid.toString().substring(0, 6);
        return userID;
    }


}
