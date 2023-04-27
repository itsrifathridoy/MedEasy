package com.medeasy.controllers;

import com.medeasy.util.DatabaseReadCall;
import com.medeasy.util.Encryption;
import com.medeasy.util.FXMLScene;
import com.medeasy.util.LoginInfoSave;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField email;

    @FXML
    private Label emailIValid;

    @FXML
    private Label passValid;

    @FXML
    private ImageView img1Success;

    @FXML
    private PasswordField password;

    @FXML
    private ImageView img2Success;

    @FXML
    private ImageView img1Wrong;

    @FXML
    private ImageView img2Wrong;

    @FXML
    private Circle smCIr1;

    @FXML
    private Circle bigCir1;
    @FXML
    private Circle bigCir2;

    @FXML
    private Circle smCir2;
    @FXML
    private Label welcomeText;

    @FXML
    private Label exclimerty;

    @FXML
    private Label infoText;
    @FXML
    private SVGPath svg;
    @FXML
    private ImageView doctorImg;

    @FXML
    private MFXProgressSpinner spinner;
    @FXML
    private BorderPane rootPane;
    @FXML
    private Circle bigCir3;
    @FXML
    private Circle bigCir31;

    @FXML
    private Circle smCir3;

    @FXML
    private Label loginFailedWarning;

    private ResultSet resultSet;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            double h = rootPane.getScene().getHeight();
            double w = rootPane.getScene().getWidth();
            double lx = 344;
            double cx = 344;
            double rx = 571.04;
            double ty=w/2;
            double by=w/2;
            String path = "M 0 " + Math.round(lx) + " C 0 " + Math.round(cx) + " 435 " + Math.round(rx) + " " + Math.round(ty) + " 0 L "+ Math.round(ty) + " " +  Math.round(h) + " L 0 "+ Math.round(h) +" Z M 0 344.1211";
            svg.setContent(path);
            doctorImg.setLayoutX(ty-247);
        });

        emailIValid.setVisible(false);
        passValid.setVisible(false);
        img1Success.setVisible(false);
        img2Success.setVisible(false);
        img1Wrong.setVisible(false);
        img2Wrong.setVisible(false);
        circleTransition(20,bigCir1,10);
        circleTransition(20,smCIr1,10);
        circleTransition(20,bigCir2,10);
        circleTransition(20,smCir2,10);
        circleTransition(20,bigCir3,10);
        circleTransition(10,smCir3,10);

        Platform.runLater(()->{
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.maximizedProperty().addListener(new ChangeListener<Boolean>() {

                @Override
                public void changed(ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                    if(t.booleanValue())
                    {
                        double h = rootPane.getScene().getHeight();
                        System.out.println(h);
                        double w = rootPane.getScene().getWidth();
                        double lx = 344;
                        double cx = 344;
                        double rx = 571.04;
                        double ty=w/2;
                        double by=w/2;
                        String path = "M 0 " + Math.round(lx) + " C 0 " + Math.round(cx) + " 435 " + Math.round(rx) + " " + Math.round(ty) + " 0 L "+ Math.round(ty) + " " +  Math.round(h) + " L 0 "+ Math.round(h) +" Z M 0 344.1211";
                        svg.setContent(path);
                        doctorImg.setLayoutX(ty-247);
                    }
                }
            });
        });




    }
    private void transition()
    {
        Task<String> task = new Task<>() {
            @Override
            protected String call() throws Exception {

                String svgPath = "";
                double h = rootPane.getScene().getHeight();
                double w = rootPane.getScene().getWidth();
                double lx = 344;
                double cx = 344;
                double rx = 571.04;
                double ty=w/2;
                double by=w/2;
                double d=(w-by)/lx;

                while (lx!=0 && cx!=0 && rx!=0 && ty!=0 && by!=0) {
                    svgPath = "M 0 " + Math.round(lx) + " C 0 " + Math.round(cx) + " 435 " + Math.round(rx) + " " + Math.round(ty) + " 0 L "+ Math.round(ty) + " " +  Math.round(h) + " L 0 "+ Math.round(h) +" Z M 0 344.1211";
                    lx--;
                    cx--;
                    rx-=1.65;
                    ty+=d;
                    by+=d;
                    try {
                        Thread.sleep(3);
                        updateValue(svgPath);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                return svgPath;
            }

        };

        task.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

                doctorImg.setTranslateX(Integer.parseInt(t1.split(" ")[8])-640);
                bigCir31.setTranslateX(Integer.parseInt(t1.split(" ")[8])-640);
                welcomeText.setLayoutX(Double.parseDouble(t1.split(" ")[8])/2-60);
                infoText.setLayoutX(Double.parseDouble(t1.split(" ")[8])/2-150);
                svg.setContent(t1);
            }
        });
        task.setOnSucceeded((e)->{
            bigCir3.setVisible(true);
            smCir3.setVisible(true);
            spinner.setLayoutX(rootPane.getPrefWidth()/2);
            spinner.setVisible(true);
        });
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();

    }
    private void circleTransition(double r,Node node,double duration)
    {
        Circle circle = new Circle(r);
        PathTransition transition = new PathTransition();
        transition.setNode(node);
        transition.setDuration(Duration.seconds(duration));
        transition.setPath(circle);
        transition.setCycleCount(PathTransition.INDEFINITE);
        transition.play();
    }
    private boolean isValidEmail(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    public void signup(ActionEvent ae) throws IOException {
        FXMLScene.switchScene("/com/medeasy/views/regBirth.fxml", (Node) ae.getSource());

    }

    public void close(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.close();
    }

    public void minimize(MouseEvent me) {
        Stage stage = (Stage) ((Node) me.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    public void login(ActionEvent actionEvent) throws IOException, InterruptedException {

        if(!isValidEmail(email.getText()) || password.getText().length()<6)
        {

            emailIValid.setVisible(true);
            passValid.setVisible(true);
        }
        else if(email.getText().equals("") || password.getText().equals(""))
        {
            emailIValid.setVisible(true);
            passValid.setVisible(true);
        }
        else if(!email.getText().equals("") && !password.getText().equals("")){
            emailIValid.setVisible(false);
            passValid.setVisible(false);


            String encryptPassword = new Encryption().getEncryptedKey(password.getText());

            String sql = "SELECT COUNT(1) FROM users WHERE email = ? AND password = ?";

            HashMap<Integer,Object> queries = new HashMap<>();
            queries.put(1,email.getText());
            queries.put(2,encryptPassword);
            DatabaseReadCall databaseReadCall = new DatabaseReadCall(sql,queries);

                databaseReadCall.setOnSucceeded(event -> {
                    resultSet = databaseReadCall.getValue();
                    try {
                        int count = 0;
                        if (resultSet.next()) {
                            count = resultSet.getInt(1);
                        }
                        if (count==1)
                        {
                            loginFailedWarning.setVisible(false);

                            transition();
                            exclimerty.setVisible(false);
                            welcomeText.setText("Loading..");

                            Task<Void> task = new Task<>() {
                                @Override
                                protected Void call() throws Exception {
                                    Thread.sleep(3000);
                                    return null;
                                }
                            };

                            task.setOnSucceeded(e->{
                                LoginInfoSave.saveLoginInfo(email.getText(),encryptPassword);
                                FXMLScene fxmlScene = FXMLScene.load("/com/medeasy/views/dashboard.fxml");
                                ((HomeController)fxmlScene.getController()).setEmail(email.getText());
                                Scene scene = new Scene(fxmlScene.getRoot());
                                Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                                stage.setScene(scene);
                                stage.show();
                            });
                            new Thread(task).start();

                        }
                        else
                        {
                            System.out.println("login Failed");
                            loginFailedWarning.setVisible(true);
                        }
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR,"Database Connection Failed");
                        alert.show();
                    }
                });
                Thread t = new Thread(databaseReadCall);
                t.start();


        }
    }
    @FXML
    void emailType(KeyEvent event) {
        if(!isValidEmail(email.getText()))
        {
            img1Success.setVisible(false);
            img1Wrong.setVisible(true);
        }
        else
        {
            img1Wrong.setVisible(false);
            img1Success.setVisible(true);
        }
    }
    @FXML
    void passType(KeyEvent event) {
        if(password.getText().length()<6)
        {
            img2Success.setVisible(false);
            img2Wrong.setVisible(true);
        }
        else
        {
            img2Wrong.setVisible(false);
            img2Success.setVisible(true);
        }
    }

}
