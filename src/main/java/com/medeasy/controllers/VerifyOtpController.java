package com.medeasy.controllers;

import com.medeasy.Main;
import com.medeasy.models.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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

public class VerifyOtpController implements Initializable {
    public HBox verifyBox;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    public String code;
    public Pane contentArea;
    public Patient patient;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            Main.enableMove(contentArea.getScene(),(Stage) contentArea.getScene().getWindow());
        });
    }

    public void setData(Patient patient, String code, Pane contentArea, ImageView img1, ImageView img2, ImageView img3)
    {
        this.patient=patient;
        this.code = code;
        this.contentArea = contentArea;
        this.img1 =img1;
        this.img2 = img2;
        this.img3 = img3;
    }


    public void verifyCodePress(KeyEvent ke) {
        System.out.println(ke.getSource());

        TextField textField = (TextField) ke.getSource();
        if (!textField.getText().matches("\\d*")) {
            textField.setText(textField.getText().replaceAll("[^\\d]", ""));
        }
        else if(textField.getText().length() > 1)
        {
            textField.setText(textField.getText().substring(0,1));

        }
        textField.end();
    }

    public void verifyBtn(ActionEvent ae) throws IOException {
        InputStream okImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8_ok_480px.png");
        InputStream thirdImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8-circled-3-240.png");
        img2.setImage(new Image(okImg));
        img3.setImage(new Image(thirdImg));

        String Vcode = "";
        for(Node tf: verifyBox.getChildren())
        {
            Vcode = Vcode + ((TextField)tf).getText();
        }
        System.out.println(Vcode);
        System.out.println(code);
        if (Vcode.equals(code))
        {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("verify_Finishing.fxml"));
            VerifyAccountController controller = new VerifyAccountController();
            controller.setData(patient,contentArea,img1,img2,img3);
            loader.setController(controller);
            Parent root = loader.load();

            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(root);
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
