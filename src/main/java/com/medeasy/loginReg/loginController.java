package com.medeasy.loginReg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {


    public ImageView maximize;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maximize.setOpacity(0.5);
    }

    public void signup(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("regBirth.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
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
