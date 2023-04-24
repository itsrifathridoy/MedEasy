package com.medeasy.loginReg;

import com.jfoenix.controls.JFXButton;
import com.medeasy.DatabaseConnection;
import com.medeasy.Main;
import com.medeasy.users.Patient;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    private String email;
    @FXML
    private Pane HooverCornner1;

    @FXML
    private Label name2;

    @FXML
    private ImageView maximize;

    @FXML
    private JFXButton donorbtn;

    @FXML
    private Label age;

    @FXML
    private Label bloodGroup;

    @FXML
    private Label weight;

    @FXML
    private Label appointmentTime;

    @FXML
    private Label reportTime;

    @FXML
    private Label medicineTime;

    @FXML
    private Label name1;

    @FXML
    private Label address;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(email);
        Platform.runLater(()->{
            Main.enableMove(age.getScene(),(Stage) age.getScene().getWindow());
            DatabaseConnection db = null;
            try {
                db = new DatabaseConnection();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                Patient patient = db.getPatient(email,"email");
                name1.setText(patient.getUsername());
                name2.setText(patient.getUsername());
                address.setText(patient.getAddressEn());
                LocalDate birthDate = LocalDate.parse(patient.getDob(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate currentDate = LocalDate.now();
                String ageStr =((Period.between(birthDate,currentDate).getYears()>0)?(Period.between(birthDate,currentDate).getYears()+" Years "):("")) + ((Period.between(birthDate,currentDate).getMonths()>0)?(Period.between(birthDate,currentDate).getMonths()+ " Months "):(""))+ ((Period.between(birthDate,currentDate).getDays()>0)?(Period.between(birthDate,currentDate).getDays()+" Days"):(""));
                age.setText(ageStr);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });


    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        System.out.println(email);
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


}
