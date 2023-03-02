package com.medeasy.loginReg;

import com.medeasy.users.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RegBirthController {
    public TextField bId;
    public DatePicker dob;
    public static Patient patient;
    public void nextDetailsView(ActionEvent actionEvent) throws IOException {
        String dateFormat = dob.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(bId.getText());
        System.out.println(dateFormat);
        patient = Patient.getPatientInfoFromApi(bId.getText(),dateFormat);

        if(patient!=null)
        {
            System.out.println(patient);

            Parent root = FXMLLoader.load(getClass().getResource("detailsView.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"No Data Found");
            alert.show();
        }

    }
}
