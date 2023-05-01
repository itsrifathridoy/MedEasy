package com.medeasy.controllers.loginReg;

import com.medeasy.util.DatabaseReadCall;
import com.medeasy.util.FXMLScene;
import com.medeasy.util.PatientApiCallTask;
import com.medeasy.models.Patient;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RegBirthController implements Initializable {

    private Object patient;
    private ImageView maximize;

    @FXML
    private BorderPane rootPane;

    @FXML
    private Label text2;

    @FXML
    private TextField bId;

    @FXML
    private MFXProgressSpinner loader;

    @FXML
    private ImageView successImg;

    @FXML
    private MFXDatePicker dob;

    @FXML
    private Label text;
    @FXML
    private Label warning;

    @FXML
    private ImageView img1Success;

    @FXML
    private ImageView img1Wrong;
    @FXML
    private Label bidValid;

    @FXML
    private Label dobValid;
    private final Stage popUPStage = new Stage();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loader.setVisible(false);
        successImg.setVisible(false);
        dobValid.setVisible(false);
        bidValid.setVisible(false);
    }


    public void nextDetailsView(ActionEvent actionEvent) throws IOException {
        warning.setVisible(false);
        if(dob.getText().equals("") || bId.getText().equals("") || dob.getValue()==null)
        {
            dobValid.setText("DOB Must be Less than "+LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            dobValid.setVisible(true);
            bidValid.setVisible(true);
        }
        else if(dob.getValue().isAfter(LocalDate.now()) || bId.getText().length()<17)
        {

            dobValid.setText("DOB Must be Less than "+LocalDate.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy")));
            dobValid.setVisible(true);
            bidValid.setVisible(true);
        }
        else if(!dob.getText().equals("") && !bId.getText().equals(""))
        {
            dobValid.setVisible(false);
            bidValid.setVisible(false);
            warning.setText("");
            ((Button) actionEvent.getSource()).setText("");
            loader.setVisible(true);
            String checkPatientAvailableSQL = "SELECT COUNT(1) FROM patients WHERE bId = ?";

            HashMap<Integer,Object> queries = new HashMap<>();
            queries.put(1,bId.getText());
            DatabaseReadCall databaseReadCall = new DatabaseReadCall(checkPatientAvailableSQL,queries);
            databaseReadCall.setOnSucceeded(wse -> {
                ResultSet resultSet = databaseReadCall.getValue();

                int count = 0;
                try {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                        System.out.println("The count is: " + count);
                    }
                    if (count == 0) {
                        String dateFormat = dob.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        System.out.println(dob.getValue());
                        PatientApiCallTask patientApiCallTask = new PatientApiCallTask(bId.getText(), dateFormat);
                        patientApiCallTask.setOnSucceeded(event -> {
                            patient = patientApiCallTask.getValue();
                            if (patient instanceof Patient) {
                                loader.setVisible(false);
                                successImg.setVisible(true);

                                System.out.println(patient);
                                FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/loginReg/detailsView.fxml");
                                ((DetailsViewController) fxmlScene.getController()).setPatient((Patient) patient);
                                Stage mainStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                ((DetailsViewController) fxmlScene.getController()).setMainStage(mainStage);
                                Scene scene = new Scene(fxmlScene.getRoot());
                                popUPStage.setScene(scene);
                                popUPStage.setResizable(false);
                                popUPStage.setAlwaysOnTop(true);
                                popUPStage.centerOnScreen();
                                popUPStage.show();
                                successImg.setVisible(false);
                                ((Button) actionEvent.getSource()).setText("Search");

                            } else if (patient == null) {
                                loader.setVisible(false);
                                warning.setVisible(true);
                                warning.setText("No Match Found");
                                ((Button) actionEvent.getSource()).setBackground(Background.fill(Color.rgb(243, 117, 121)));
                                ((Button) actionEvent.getSource()).setText("Try Again");

                            } else if (patient instanceof String[]) {
                                String[] strings = (String[]) patient;
                                loader.setVisible(false);
                                ((Button) actionEvent.getSource()).setBackground(Background.fill(Color.rgb(243, 117, 121)));
                                ((Button) actionEvent.getSource()).setText("Try Again");
                                Alert alert = new Alert(Alert.AlertType.ERROR, strings[1], ButtonType.OK);
                                alert.setHeaderText(strings[0]);
                                alert.setTitle("Unable to Search");
                                alert.show();
                            }

                        });
                        patientApiCallTask.setOnFailed(event -> {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Error");
                            alert.show();
                        });
                        new Thread(patientApiCallTask).start();
                    }
                    else {
                        warning.setText("Already account created");
                        warning.setVisible(true);
                        loader.setVisible(false);
                        ((Button) actionEvent.getSource()).setText("Search");
                    }

                }
                catch (Exception e)
                {
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

    }

    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }


    @FXML
    void bidType(KeyEvent event) {
        TextField textField = (TextField) event.getSource();
        if (!textField.getText().matches("\\d*")) {
            textField.setText(textField.getText().replaceAll("[^\\d]", ""));
            textField.end();
        }
        else if(textField.getText().length() > 17)
        {
            textField.setText(textField.getText().substring(0,17));
        }
        else if(textField.getText().length()<17)
        {
            img1Wrong.setVisible(true);
            img1Success.setVisible(false);
        }
        if(textField.getText().length() ==17)
        {
            img1Wrong.setVisible(false);
            img1Success.setVisible(true);
            textField.end();
        }
    }
}
