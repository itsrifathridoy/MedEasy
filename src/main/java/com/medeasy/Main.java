package com.medeasy;

import com.medeasy.controllers.admin.AdminHomeController;
import com.medeasy.controllers.DashboardController;
import com.medeasy.controllers.doctor.DoctorHomeController;
import com.medeasy.controllers.patient.PatientHomeController;
import com.medeasy.util.DatabaseConnection;
import com.medeasy.util.FXMLScene;
import com.medeasy.util.LoginInfoSave;
import javafx.application.Application;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        String[] loginInfo = LoginInfoSave.getLoginInfo();
        String email = loginInfo[0];
        String password = loginInfo[1];
        String role = loginInfo[2];
        String userID = loginInfo[3];
        boolean isAutoLogin = false;
        if(email!=null && password!=null && role!=null)
        {
            isAutoLogin = isSaved(email,password);

        }
        if(isAutoLogin && role.equals("PATIENT"))
        {

            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/patients/patientHome.fxml");
            PatientHomeController controller = (PatientHomeController) fxmlScene.getController();
            controller.setUserID(userID);
            Scene scene = new Scene(fxmlScene.getRoot(), Color.TRANSPARENT);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else if(isAutoLogin && role.equals("ADMIN"))
        {

            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/admin/adminHome.fxml");
            Scene scene = new Scene(fxmlScene.getRoot(), Color.TRANSPARENT);
            AdminHomeController controller = (AdminHomeController) fxmlScene.getController();
            controller.setEmail(email);
            controller.setUserID(userID);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else if(isAutoLogin && role.equals("DOCTOR"))
        {

            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/doctors/doctorHome.fxml");
            DoctorHomeController controller = (DoctorHomeController) fxmlScene.getController();
            controller.setDoctorID(userID);
            Scene scene = new Scene(fxmlScene.getRoot(), Color.TRANSPARENT);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
        else {
            FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/loginReg/login.fxml");
            Scene scene = new Scene(fxmlScene.getRoot(), Color.TRANSPARENT);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.show();
        }
    }
    public static boolean isSaved(String email,String password) throws SQLException, ClassNotFoundException {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection connection = databaseConnection.getConnection();

        String sql = "SELECT COUNT(1) FROM users WHERE email = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1,email);
        statement.setString(2,password);
        ResultSet resultSet = statement.executeQuery();
        int count = 0;
        if (resultSet.next()) {
            count = resultSet.getInt(1);
        }
        if (count==1) {
            return true;
        }
        else
        {
            return false;
        }

    }
    public static void enableMove(Scene scene, Stage primaryStage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        scene.setOnMousePressed(event -> {
            xOffset.set(primaryStage.getX() - event.getScreenX());
            yOffset.set(primaryStage.getY() - event.getScreenY());
            scene.setCursor(Cursor.CLOSED_HAND);
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset.get());
            primaryStage.setY(event.getScreenY() + yOffset.get());
        });


        scene.setOnMouseReleased(event -> {
            scene.setCursor(Cursor.DEFAULT);
        });
    }
}
