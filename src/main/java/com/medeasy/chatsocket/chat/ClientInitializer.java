package com.medeasy.chatsocket.chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : Kavishka Prabath
 * @since : 0.1.0
 **/

public class ClientInitializer extends Application {

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/teamvoid/gocircle/fxml/chatbox.fxml")));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Play Tech Chat Application");

        enableMove(scene, primaryStage);
        primaryStage.show();
    }

    private void enableMove(Scene scene, Stage primaryStage) {
        AtomicReference<Double> xOffset = new AtomicReference<>((double) 0);
        AtomicReference<Double> yOffset = new AtomicReference<>((double) 0);
        scene.setOnMousePressed(event -> {
            xOffset.set(primaryStage.getX() - event.getScreenX());
            yOffset.set(primaryStage.getY() - event.getScreenY());
        });
        //Lambda mouse event handler
        scene.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() + xOffset.get());
            primaryStage.setY(event.getScreenY() + yOffset.get());
        });
    }

}
