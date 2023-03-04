package com.medeasy.loginReg;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {


    public void signup(ActionEvent ae) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("regBirth.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
