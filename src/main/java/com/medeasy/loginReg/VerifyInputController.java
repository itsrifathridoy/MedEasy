package com.medeasy.loginReg;

import com.medeasy.Main;
import com.medeasy.SendEmail;
import com.medeasy.users.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class VerifyInputController implements Initializable {
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public HBox verifyBox;
    public TextField username;
    public TextField email;
    public Pane contentArea;
    String code = "";
    Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            Main.enableMove(contentArea.getScene(),(Stage) contentArea.getScene().getWindow());
        });
    }
    public void setPatient(Patient patient) {
        this.patient = patient;
    }




    public void nextVerify(ActionEvent ae) throws Exception {


        InputStream okImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8_ok_480px.png");
        InputStream secondImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8-circled-2-240.png");
        img1.setImage(new Image(okImg));
        img2.setImage(new Image(secondImg));

        code = String.valueOf((int)(Math.random()*900000)+100000);
        String userName=this.username.getText();
        System.out.println(userName);
        System.out.println(code);
        String htmlCode = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"><meta name=\"viewport\" content=\"width=device-width,initial-scale=1\"><title></title><link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Rubik\"><style type=\"text/css\">@media screen{@font-face{font-family:Rubik;font-style:normal;font-weight:400;src:url(https://fonts.gstatic.com/s/rubik/v11/iJWZBXyIfDnIV5PNhY1KTN7Z-Yh-B4iFV0Uz.woff) format('woff')}}@media only screen and (max-width:620px){.wrapper .section{width:100%}.wrapper .column{width:100%;display:block}}</style></head><body style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"></p><table width=\"100%\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"wrapper\" width=\"600\" align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;padding-left:10px;padding-right:10px;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table class=\"section header\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:initial;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:1px solid #c3cdc9;border-radius:8px;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding-top:64px\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:center;padding-top:32px;padding-bottom:3px\">Verification Code</h2><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;margin-bottom:48px\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;vertical-align:middle\"><h2 style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;padding:0\">&nbsp;&nbsp;MedEasy</h2></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"left\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;border-top:1px solid #c3cdc9;padding:46px 54px 64px\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;text-align:left\">Hello, "+userName+"!</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Enter the following OTP to create a new account</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-weight:600;font-size:24px;text-align:center\">"+code+"</p><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;text-align:left\">Do not forward or give this code to anyone.<br></p></td></tr></tbody></table></td></tr><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td class=\"column\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><table style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;width:100%;border-bottom:1px solid #c3cdc9\"><tbody style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><tr style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><td align=\"center\" style=\"padding:0;margin:0;border:none;border-spacing:0;border-collapse:collapse;vertical-align:top;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important\"><p style=\"margin:0;padding:0;padding-bottom:20px;line-height:1.6;font-family:Rubik;color:#2d4f43;font-family:Rubik,'Segoe UI',Tahoma,Geneva,Verdana,sans-serif!important;font-size:14px;padding-bottom:32px\">For any concerns, please reach out MedEasy support Team</p></td></tr></tbody></table></td></tr></tbody></table></td></tr></tbody></table></body></html>";
        System.out.println(email.getText());
        SendEmail.sendMail(email.getText(),"MedEasy Veification Code",htmlCode);


        FXMLLoader loader = new FXMLLoader(getClass().getResource("verify_otp.fxml"));
        VerifyOtpController controller = new VerifyOtpController();
        controller.setData(new Patient(patient,userName,email.getText()),code,contentArea,img1,img2,img3);
        loader.setController(controller);
        Parent root = loader.load();

        contentArea.getChildren().removeAll();
        contentArea.getChildren().setAll(root);
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
