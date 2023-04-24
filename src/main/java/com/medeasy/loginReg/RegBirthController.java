package com.medeasy.loginReg;

import com.jfoenix.controls.JFXDatePicker;
import com.medeasy.Main;
import com.medeasy.PatientApiCallTask;
import com.medeasy.users.Patient;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class RegBirthController implements Initializable {
    public TextField bId;
    public Patient patient;
    public ImageView maximize;
    public Label text;
    public MFXDatePicker dob;
    public MFXProgressSpinner loader;
    public ImageView successImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader.setVisible(false);
        successImg.setVisible(false);
        Platform.runLater(()->{
            Main.enableMove(bId.getScene(),(Stage) bId.getScene().getWindow());
        });
    }

    public void nextDetailsView(ActionEvent actionEvent) throws IOException {

        ((Button)actionEvent.getSource()).setText("");
        loader.setVisible(true);

        String dateFormat = dob.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println(bId.getText());
        System.out.println(dateFormat);
        PatientApiCallTask patientApiCallTask = new PatientApiCallTask(bId.getText(),dateFormat);
        patientApiCallTask.setOnSucceeded(event -> {


            patient = patientApiCallTask.getValue();
            if(patient!=null)
            {
                loader.setVisible(false);
                ((Button)actionEvent.getSource()).setBackground(Background.fill(Color.rgb(26, 181, 141)));
                successImg.setVisible(true);

                System.out.println(patient);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("detailsView.fxml"));
                DetailsViewController controller = new DetailsViewController();
                controller.setPatient(patient);
                loader.setController(controller);
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            }
            else
            {
//                Alert alert = new Alert(Alert.AlertType.ERROR,"No Data Found");
//                alert.show();
                loader.setVisible(false);
                ((Button)actionEvent.getSource()).setBackground(Background.fill(Color.rgb(243, 117, 121)));
                ((Button)actionEvent.getSource()).setText("Try Again");

            }

        });
        patientApiCallTask.setOnFailed(event -> {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Error");
            alert.show();
        });

        new Thread(patientApiCallTask).start();



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
