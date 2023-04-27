package com.medeasy.controllers;

import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.Encryption;
import com.medeasy.Main;
import com.medeasy.models.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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
import java.util.ResourceBundle;

public class VerifyAccountController implements Initializable {

    public TextField password;
    public TextField confirmPassword;
    private Patient patient;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public Pane contentArea;

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

    public void createAccount(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {

        if (password.getText().equals(confirmPassword.getText())) {
            String sql = "INSERT INTO patients (bId, name, fatherNameBn, motherNameBn, dob, email, addressBn, addressEn, officeNameBn, officeNameEn, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            String sql2 = "SELECT COUNT(1) FROM patients WHERE bId = '"+patient.getbId()+"'";

            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            Statement statement1 = connection.createStatement();
            ResultSet resultSet = statement1.executeQuery(sql2);
            int count = 0;
            if (resultSet.next()) {
                count = resultSet.getInt(1);
                System.out.println("The count is: " + count);
            }
            if (count==0)
            {
                try {
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, patient.getbId());
                    statement.setString(2, patient.getPersonNameBn());
                    statement.setString(3, patient.getPersonNameEn());
                    statement.setString(4, patient.getFatherNameBn());
                    statement.setString(5, patient.getFatherNameEn());
                    statement.setString(6, patient.getMotherNameBn());
                    statement.setString(7, patient.getMotherNameEn());
                    statement.setString(8, patient.getDob());
                    statement.setString(9, patient.getEmail());
                    statement.setString(10, patient.getAddressBn());
                    statement.setString(11, patient.getAddressEn());
                    statement.setString(12, patient.getOfficeNameBn());
                    statement.setString(13, patient.getOfficeNameEn());
                    statement.setString(14, patient.getUsername());
                    String encryptedSecret = new Encryption().getEncryptedKey(password.getText());
                    statement.setString(15, encryptedSecret);
                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                        Parent root = loader.load();
                        HomeController homeController = loader.getController();
                        homeController.setEmail(patient.getEmail());
                        Scene scene = new Scene(root);
                        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.centerOnScreen();
                        stage.show();
                        System.out.println("A new patient has been inserted.");
                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Email already available on db");
                        alert.show();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else
            {
                System.out.println("Create account Failed");
                Alert alert = new Alert(Alert.AlertType.ERROR,"Account already created");
                alert.show();
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
