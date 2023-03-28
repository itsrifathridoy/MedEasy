package com.medeasy.loginReg;

import com.medeasy.DatabaseConnection;
import com.medeasy.Encryption;
import com.medeasy.users.Patient;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VerifyAccountController {

    public TextField password;
    public TextField confirmPassword;
    private Patient patient;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public Pane contentArea;

    public void setData(Patient patient, Pane contentArea, ImageView img1, ImageView img2, ImageView img3) {
        this.patient = patient;
        this.contentArea = contentArea;
        this.img1 = img1;
        this.img2 = img2;
        this.img3 = img3;
    }

    public void createAccount(ActionEvent actionEvent) {

        if (password.getText().equals(confirmPassword.getText())) {
            String sql = "INSERT INTO patients (bId, name, fatherNameBn, motherNameBn, dob, email, addressBn, addressEn, officeNameBn, officeNameEn, username, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1, patient.getbId());
                statement.setString(2, patient.getPatientName());
                statement.setString(3, patient.getFatherNameBn());
                statement.setString(4, patient.getMotherNameBn());
                statement.setString(5, patient.getDob());
                statement.setString(6, patient.getEmail());
                statement.setString(7, patient.getAddressBn());
                statement.setString(8, patient.getAddressEn());
                statement.setString(9, patient.getOfficeNameBn());
                statement.setString(10, patient.getOfficeNameEn());
                statement.setString(11, patient.getUsername());
                String encryptedSecret = new Encryption().getEncryptedKey(password.getText());
                statement.setString(12, encryptedSecret);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("A new patient has been inserted.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
