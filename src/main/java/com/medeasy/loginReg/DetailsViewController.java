package com.medeasy.loginReg;

import com.medeasy.users.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DetailsViewController implements Initializable {
    public Label name;
    public Label dob;
    public Label fName;
    public Label mName;
    public Label address;
    public Label address1;
    private Patient patient;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(patient.getPatientName());
        dob.setText(patient.getDob());
        fName.setText(patient.getFatherNameBn());
        mName.setText(patient.getMotherNameBn());
        String[] str =patient.getAddressBn().split(",");
        String temp = "";
        for(int i=0; i<str.length-2;i++)
        {
            temp =  temp + str[i] + ",";
        }
        address.setText(temp);
        address1.setText(str[str.length-2]+ "," + str[str.length-1]);
        System.out.println(patient);

    }


    public void nextCreateAccount(ActionEvent actionEvent) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("verify_input.fxml"));
//        Scene scene = new Scene(root);
//        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//        stage.setScene(scene);
//        stage.show();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("verify_input.fxml"));
        VerifyInputController controller = new VerifyInputController();
        controller.setPatient(patient);
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
