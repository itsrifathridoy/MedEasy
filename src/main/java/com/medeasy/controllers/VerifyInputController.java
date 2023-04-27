package com.medeasy.controllers;

import com.medeasy.util.DatabaseReadCall;
import com.medeasy.util.FXMLScene;
import com.medeasy.util.SendEmail;
import com.medeasy.models.Patient;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

public class VerifyInputController implements Initializable {
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
    private TextField username;

    @FXML
    private MFXProgressSpinner loader;

    @FXML
    private ImageView img1Success;

    @FXML
    private ImageView img2Success;

    @FXML
    private ImageView img1Wrong;

    @FXML
    private ImageView img2Wrong;

    @FXML
    private Label usernameValid;

    @FXML
    private Label emailIValid;
    @FXML
    private Label emailExist;
    private String code = "";
    private Patient patient;
    private Stage mainStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailExist.setVisible(false);
        emailIValid.setVisible(false);
        usernameValid.setVisible(false);
        img1Success.setVisible(false);
        img2Success.setVisible(false);
        img1Wrong.setVisible(false);
        img2Wrong.setVisible(false);
        loader.setVisible(false);
//        Platform.runLater(()->{
//            Main.enableMove(contentArea.getScene(),(Stage) contentArea.getScene().getWindow());
//        });
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
        if(!patient.getPersonNameEn().equals(""))
        {
            username.setText(patient.getPersonNameEn());
        }
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public void nextVerify(ActionEvent ae) throws Exception {
        emailExist.setVisible(false);
        if(!isValidEmail(email.getText()) || username.getText().length()<2)
        {

            emailIValid.setVisible(true);
            usernameValid.setVisible(true);
        }
        else if(email.getText().equals("") || username.getText().equals(""))
        {
            emailIValid.setVisible(true);
            usernameValid.setVisible(true);
        }
        else if(!email.getText().equals("") && !username.getText().equals("")) {
            String checkUserAvailabilitySQL = "SELECT COUNT(1) FROM users WHERE email = ?";
            HashMap<Integer,Object> queries = new HashMap<>();
            queries.put(1,email.getText());
            DatabaseReadCall databaseReadCall = new DatabaseReadCall(checkUserAvailabilitySQL,queries);
            databaseReadCall.setOnSucceeded(wse -> {
                ResultSet resultSet = databaseReadCall.getValue();
                int count = 0;
                try {
                    if (resultSet.next()) {
                        count = resultSet.getInt(1);
                        System.out.println("The count is: " + count);
                    }
                    if(count==0)
                    {
                        ((Button)ae.getSource()).setText("");
                        loader.setVisible(true);
                        emailIValid.setVisible(false);
                        usernameValid.setVisible(false);

                        code = String.valueOf((int) (Math.random() * 900000) + 100000);
                        String userName = this.username.getText();
                        System.out.println(userName);
                        System.out.println(code);
                        String htmlCode = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title></title><link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Rubik\"><style type=\"text/css\">@media screen{@font-face{font-family:Rubik;font-style:normal;font-weight:400;src:url(https://fonts.gstatic.com/s/rubik/v11/iJWZBXyIfDnIV5PNhY1KTN7Z-Yh-B4iFV0Uz.woff) format('woff')}}@media only screen and (max-width:620px){.wrapper .section{width:100%}.wrapper .column{width:100%;display:block}}</style></head><body style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"></p><table width=\"100%\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"wrapper\" width=\"600\" align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;padding-left:10px;padding-right:10px;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table class=\"section header\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:initial;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:1px solid #c3cdc9;border-radius:8px;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding-top:64px\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:center;padding-top:32px;padding-bottom:3px\">Verification Code</h2><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;margin-bottom:48px\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;vertical-align:middle\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding:0\">&nbsp;&nbsp;MedEasy</h2></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"left\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;border-top:1px solid #c3cdc9;padding:46px 54px 64px\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;text-align:left\">Hello, " + userName + "!</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Enter the following OTP to create a new account</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;font-size:24px;text-align:center\">" + code + "</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Do not forward or give this code to anyone.<br></p></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;width:100%;border-bottom:1px solid #c3cdc9\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-size:14px;padding-bottom:32px\">For any concerns, please reach out MedEasy support Team</p></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";
                        System.out.println(email.getText());
                        SendEmail sendOtp = new SendEmail(email.getText(), "MedEasy Veification Code", htmlCode);
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
                            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/verify_otp.fxml");
                            VerifyOtpController controller = (VerifyOtpController) fxmlScene.getController();
                            controller.setData(new Patient(patient, userName, email.getText()), code, contentArea, img1, img2, img3);
                            controller.setMainStage(mainStage);

                            contentArea.getChildren().removeAll();
                            contentArea.getChildren().setAll(fxmlScene.getRoot());
                        });
                        sendOtp.setOnFailed(workerStateEvent -> {
                            loader.setVisible(false);
                            ((Button)ae.getSource()).setText("Next");
                        });
                        Thread thread = new Thread(sendOtp);
                        thread.setDaemon(true);
                        thread.start();
                    }
                    else
                    {
                        emailExist.setVisible(true);
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
    void usernameType(KeyEvent event) {
        if(username.getText().length()<2)
        {
            img1Success.setVisible(false);
            img1Wrong.setVisible(true);
        }
        else
        {
            img1Wrong.setVisible(false);
            img1Success.setVisible(true);
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
