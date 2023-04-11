package com.medeasy.loginReg;

import com.medeasy.DatabaseConnection;
import com.medeasy.Encryption;
import com.medeasy.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public ImageView maximize;
    public TextField email;
    public CheckBox remember;
    public PasswordField password;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        maximize.setOpacity(0.5);
        Platform.runLater(()->{
            Main.enableMove(email.getScene(),(Stage) email.getScene().getWindow());
        });

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

    public void login(ActionEvent actionEvent) throws IOException {
        if (!email.getText().isEmpty() && !password.getText().isEmpty()) {
            String sql = "SELECT COUNT(1) FROM patients WHERE email = ? AND password = ?";
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, email.getText());
                String encryptedKey = new Encryption().getEncryptedKey(password.getText());
                statement.setString(2, encryptedKey);
                System.out.println(email.getText());
                System.out.println(password.getText());
                System.out.println(encryptedKey);

                ResultSet resultSet = statement.executeQuery();
                int count = 0;
                if (resultSet.next()) {
                    count = resultSet.getInt(1);
                    System.out.println("The count is: " + count);
                }
                if (count==1)
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                    Parent root = loader.load();
                    HomeController homeController = loader.getController();
                    homeController.setEmail(email.getText());
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.centerOnScreen();
                    stage.show();
                }
                else
                {
                    System.out.println("login Failed");
                    Alert alert = new Alert(Alert.AlertType.ERROR,"Wrong Email and Password");
                    alert.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR,"Email and Password should not be empty");
            alert.show();

        }
    }
}
