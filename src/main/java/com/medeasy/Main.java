package com.medeasy;

import com.medeasy.users.Patient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("loginReg/login.fxml"));
        Scene scene = new Scene(root, Color.TRANSPARENT);
//        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
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
