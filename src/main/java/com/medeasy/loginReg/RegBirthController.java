package com.medeasy.loginReg;

import com.medeasy.users.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class RegBirthController {
    public TextField bId;
    public static Patient patient;
    public ImageView maximize;
    public Label text;
    public MFXDatePicker dob;

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

    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
}
