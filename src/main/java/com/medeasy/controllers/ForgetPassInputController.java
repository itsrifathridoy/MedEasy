package com.medeasy.controllers;

import com.medeasy.models.EmailTemplate;
import com.medeasy.models.Patient;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.DatabaseReadCall;
import com.medeasy.util.FXMLScene;
import com.medeasy.util.SendEmail;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ForgetPassInputController implements Initializable {

    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;

    @FXML
    private ImageView img3;

    @FXML
    private Pane contentArea;

    @FXML
    private TextField email;

    @FXML
    private MFXProgressSpinner loader;

    @FXML
    private ImageView img2Success;

    @FXML
    private ImageView img2Wrong;

    @FXML
    private Label emailExist;
    @FXML
    private Label emailIValid;
    private Stage mainStage;
    private String code;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailExist.setVisible(false);
        emailIValid.setVisible(false);
        img2Success.setVisible(false);
        img2Wrong.setVisible(false);
        loader.setVisible(false);
    }

    @FXML
    void emailType(KeyEvent event) {
        if(!isValidEmail(email.getText()))
        {
            img2Success.setVisible(false);
            img2Wrong.setVisible(true);
        }
        else
        {
            img2Wrong.setVisible(false);
            img2Success.setVisible(true);
        }
    }

    @FXML
    void nextVerify(ActionEvent event) {
        emailExist.setVisible(false);
        if(email.getText().equals("") || !isValidEmail(email.getText()))
        {
            emailIValid.setVisible(true);
        }
        else if(!email.getText().equals("") && isValidEmail(email.getText())) {
            ((Button)event.getSource()).setText("");
            loader.setVisible(true);
            emailIValid.setVisible(false);
            String checkUserAvailabilitySQL = "SELECT COUNT(1) FROM users WHERE email = ?";
            HashMap<Integer,Object> queries = new HashMap<>();
            queries.put(1,email.getText());
            DatabaseReadCall databaseReadCall = new DatabaseReadCall(checkUserAvailabilitySQL,queries);
            databaseReadCall.setOnSucceeded(wse -> {
                ResultSet resultSet = databaseReadCall.getValue();
                System.out.println(queries);
                int count = 0;
                try {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                        System.out.println("The count is: " + count);
                    }
                    if(count==1)
                    {
                        String getUserNameSQL = "SELECT username from patients WHERE email = ?";
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        Connection connection = databaseConnection.getConnection();
                        PreparedStatement statement = connection.prepareStatement(getUserNameSQL);
                        statement.setString(1,email.getText());
                        ResultSet resultSet1 = statement.executeQuery();
                        String name = null;
                        while (resultSet1.next())
                        {
                            name = resultSet1.getString(1);
                        }
                        code = String.valueOf((int) (Math.random() * 900000) + 100000);
                        System.out.println(code);
                        System.out.println(email.getText());
                        EmailTemplate emailTemplate = new EmailTemplate(name,code);
                        SendEmail sendOtp = new SendEmail(email.getText(), "MedEasy Veification Code", emailTemplate.getForgetPassTemplate());
                        sendOtp.setOnSucceeded(workerStateEvent -> {
                            InputStream okImg = null;
                            InputStream secondImg = null;
                            try {
                                okImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8_ok_480px.png");
                                secondImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8-circled-2-240.png");

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            img1.setImage(new Image(okImg));
                            img2.setImage(new Image(secondImg));
                            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/forgetPassword_otp.fxml");
                            ForgetPasswordOtpController controller = (ForgetPasswordOtpController) fxmlScene.getController();
                            controller.setData(email.getText(),code,contentArea,img1,img2,img3);
                            controller.setMainStage(mainStage);
                            contentArea.getChildren().removeAll();
                            contentArea.getChildren().setAll(fxmlScene.getRoot());
                        });
                        sendOtp.setOnFailed(workerStateEvent -> {
                            loader.setVisible(false);
                            ((Button)event.getSource()).setText("Next");
                        });
                        Thread thread = new Thread(sendOtp);
                        thread.setDaemon(true);
                        thread.start();

                    }
                    else
                    {
                        emailExist.setVisible(true);
                        loader.setVisible(false);
                        ((Button)event.getSource()).setText("Next");
                    }
                }catch (Exception e)
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
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }



}
