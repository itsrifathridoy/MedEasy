package com.medeasy.controllers.loginReg;

import com.medeasy.controllers.DashboardController;
import com.medeasy.util.*;
import com.medeasy.Main;
import com.medeasy.models.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

public class VerifyAccountController implements Initializable {

    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    private Patient patient;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    @FXML
    private Pane contentArea;
    private Stage mainStage;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            Main.enableMove(contentArea.getScene(),(Stage) contentArea.getScene().getWindow());
        });
    }

    public void setData(Patient patient, Pane contentArea, ImageView img1, ImageView img2, ImageView img3) {
        this.patient = patient;
        this.contentArea = contentArea;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }
    private String generateUserID(String role) {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Dhaka"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        String timestamp = now.format(formatter);
        UUID uuid = UUID.randomUUID();
        String userID = role + "_" + timestamp + uuid.toString().substring(0, 6);
        return userID;
    }

    public void createAccount(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        if (password.getText().equals(confirmPassword.getText())) {
            String userCreateSQL = "INSERT INTO users (userID, email, password, role) VALUES (?, ?, ?, ?)";
            String patientCreateSQL = "INSERT INTO patients (userID, bId, personNameBn, personNameEn, fatherNameBn, fatherNameEn, motherNameBn, motherNameEn, dob, email, addressBn, addressEn, officeNameBn, officeNameEn, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
                try {
                    String userId = generateUserID("PATIENT");
                    String encryptedSecret = new Encryption().getEncryptedKey(password.getText());
                    HashMap<Integer,Object> userCreateQuery = new HashMap<>();
                    userCreateQuery.put(1,userId);
                    userCreateQuery.put(2,patient.getEmail());
                    userCreateQuery.put(3,encryptedSecret);
                    userCreateQuery.put(4,"PATIENT");
                    DatabaseWriter databaseWriter = new DatabaseWriter(userCreateSQL,userCreateQuery);
                    databaseWriter.join();
                    int userRowInserted = databaseWriter.getRowInserted();

                    //Add data on patients table

                    HashMap<Integer,Object> patientCreateQuery = new HashMap<>();
                    patientCreateQuery.put(1, userId);
                    patientCreateQuery.put(2, patient.getbId());
                    patientCreateQuery.put(3, patient.getPersonNameBn());
                    patientCreateQuery.put(4, patient.getPersonNameEn());
                    patientCreateQuery.put(5, patient.getFatherNameBn());
                    patientCreateQuery.put(6, patient.getFatherNameEn());
                    patientCreateQuery.put(7, patient.getMotherNameBn());
                    patientCreateQuery.put(8, patient.getMotherNameEn());
                    patientCreateQuery.put(9, patient.getDob());
                    patientCreateQuery.put(10, patient.getEmail());
                    patientCreateQuery.put(11, patient.getAddressBn());
                    patientCreateQuery.put(12, patient.getAddressEn());
                    patientCreateQuery.put(13, patient.getOfficeNameBn());
                    patientCreateQuery.put(14, patient.getOfficeNameEn());
                    patientCreateQuery.put(15, patient.getUsername());

                    DatabaseWriter patientDatabaseWriter = new DatabaseWriter(patientCreateSQL,patientCreateQuery);
                    patientDatabaseWriter.join();
                    int patientRowsInserted = databaseWriter.getRowInserted();

                    if (patientRowsInserted > 0 && userRowInserted>0) {
                        LoginInfoSave.saveLoginInfo(patient.getEmail(), encryptedSecret,"PATIENT",userId);
                        FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/dashboard.fxml");
                        DashboardController dashboardController = (DashboardController) fxmlScene.getController();
                        dashboardController.setEmail(patient.getEmail());
                        Scene scene = new Scene(fxmlScene.getRoot());
                        mainStage.setScene(scene);
                        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                        stage.close();
                        mainStage.show();
                        System.out.println("A new patient has been inserted.");
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Email already available on db");
                        alert.show();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

        } else {
            System.out.println("Please Enter Your Password");
        }

    }
    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
