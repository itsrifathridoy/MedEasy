package com.medeasy.util;

import javafx.animation.FadeTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class FXMLScene{
    private Parent root = null;
    private Object controller = null;

    public static FXMLScene load(String fxmlpath) {
        FXMLScene fxmlScene = new FXMLScene();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(); // creates a basic fxml object here
            fxmlLoader.setLocation(fxmlScene.getClass().getResource(fxmlpath)); // sets the fxml loader to new class

            fxmlScene.root =  fxmlLoader.load();
            fxmlScene.controller = fxmlLoader.getController(); // gets the controller class of new fxml scene
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return fxmlScene;

    }

    public static void switchScene(String fxmlPath, Node node) {
            FXMLScene fxmlScene = FXMLScene.load(fxmlPath);
            Scene scene = new Scene(fxmlScene.getRoot());
            Stage stage = (Stage) node.getScene().getWindow();
            stage.setScene(scene);
            stage.show();

    }
    public static void switchScene(FXMLScene fxmlScene, Node node) {
        Scene scene = new Scene(fxmlScene.getRoot());
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }
    public static void switchScene(String fxmlPath, Stage stage) throws IOException {
        FXMLScene fxmlScene = FXMLScene.load(fxmlPath);
        Scene scene = new Scene(fxmlScene.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    public Parent getRoot() {
        return root;
    }

    public Object getController() {
        return controller;
    }
}
