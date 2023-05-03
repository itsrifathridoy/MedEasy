package com.medeasy.controllers.patient;

import com.medeasy.models.Record;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RecordCardController implements Initializable {
    @FXML
    private Label title;

    @FXML
    private Label type;

    @FXML
    private Label doctorName;

    @FXML
    private Label date;
    private Record record;

    public void setRecord(Record record) {
        this.record = record;
    }

    @FXML
    void view(ActionEvent event) throws IOException, SQLException {
        String path = "temp" +record.getRecordID()+ ".pdf";


        if(record.getRecordID()!=null) {
            byte byteArray[] = record.getRecordLink().getBytes(1, (int) record.getRecordLink().length());
            FileOutputStream outPutStream = new FileOutputStream(path);
            outPutStream.write(byteArray);
            outPutStream.close();
            File file =new File(path);
            if(file.exists())
            {
                Desktop.getDesktop().open(file);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            title.setText(record.getTitle());
            type.setText(record.getType());
            doctorName.setText(record.getDoctorName());
            date.setText(record.getDate());
        });
    }
}
