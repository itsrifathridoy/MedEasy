package com.medeasy.controllers.loginReg;

import com.medeasy.util.*;
import com.medeasy.Main;
import com.medeasy.models.Patient;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.UUID;

public class ForgetPasswordChangePassController implements Initializable {

    @FXML
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Label warning;
    private Patient patient;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    @FXML
    private Pane contentArea;
    private Stage mainStage;
    private String email;

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        warning.setVisible(false);

//        Platform.runLater(()->{
//            Main.enableMove(contentArea.getScene(),(Stage) contentArea.getScene().getWindow());
//        });
    }

    public void setData(String email) {
        this.email = email;
    }

    public void changePassword(ActionEvent actionEvent) throws SQLException, IOException, ClassNotFoundException {
        warning.setVisible(false);
        if (password.getText().equals(confirmPassword.getText())) {
            String updatePasswordSQL = "UPDATE users SET `password` = ? WHERE email = ?";
            HashMap<Integer,Object> passMap = new HashMap<>();
            passMap.put(1,new Encryption().getEncryptedKey(password.getText()));
            passMap.put(2,email);
            try {
                DatabaseWriter databaseWriter = new DatabaseWriter(updatePasswordSQL,passMap);
                databaseWriter.join();
                int rowInserted = databaseWriter.getRowInserted();
                if(rowInserted>0)
                {
                    System.out.println("Password Changed");
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"Password Changed", ButtonType.OK);
                    alert.setTitle("Changed Password");
                    alert.setHeaderText("Your Password has benn changed successfully");
                    alert.show();
                    stage.close();
                }
                else
                {
                    System.out.println("Failed to change password");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } else {
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
