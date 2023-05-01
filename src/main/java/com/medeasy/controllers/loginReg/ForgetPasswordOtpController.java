package com.medeasy.controllers.loginReg;

import com.medeasy.util.FXMLScene;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
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

public class ForgetPasswordOtpController implements Initializable {
    @FXML
    private HBox verifyBox;
    @FXML
    private Label warning;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private String code;
    private Pane contentArea;
    private Stage mainStage;
    private String email;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warning.setVisible(false);
    }

    public void setData(String email, String code, Pane contentArea, ImageView img1, ImageView img2, ImageView img3)
    {
        this.email = email;
        this.code = code;
        this.contentArea = contentArea;
        this.img1 =img1;
        this.img2 = img2;
        this.img3 = img3;
    }



    public void verifyCodePress(KeyEvent ke) {
        for (int i = 0; i < verifyBox.getChildren().size(); i++) {
            if (verifyBox.getChildren().get(i) instanceof TextField && verifyBox.getChildren().get(i).isFocused()) {

                TextField textField = (TextField) ke.getSource();
                if (!textField.getText().matches("\\d*")) {
                    textField.setText(textField.getText().replaceAll("[^\\d]", ""));
                } else if (textField.getText().length() > 1) {
                    textField.setText(textField.getText().substring(0, 1));

                } else if (textField.getText().length() == 1 && i!=verifyBox.getChildren().size()-1) {
                    verifyBox.getChildren().get(i + 1).requestFocus();
                    break;
                }
            }


        }
    }

    public void verifyBtn(ActionEvent ae) throws IOException {
        warning.setVisible(false);
        String Vcode = "";
        for(Node tf: verifyBox.getChildren())
        {
            Vcode = Vcode + ((TextField)tf).getText();
        }
        System.out.println(Vcode);
        System.out.println(code);
        if (Vcode.equals(code))
        {
            InputStream okImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8_ok_480px.png");
            InputStream thirdImg = new FileInputStream("src/main/resources/com/medeasy/img/icons8-circled-3-240.png");
            img2.setImage(new Image(okImg));
            img3.setImage(new Image(thirdImg));
            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/loginReg/forgetPassword_changePass.fxml");
            ForgetPasswordChangePassController controller = (ForgetPasswordChangePassController) fxmlScene.getController();
            controller.setMainStage(mainStage);
            controller.setData(email);
            controller.setMainStage(mainStage);

            contentArea.getChildren().removeAll();
            contentArea.getChildren().setAll(fxmlScene.getRoot());
        }
        else
        {
            warning.setVisible(true);
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
