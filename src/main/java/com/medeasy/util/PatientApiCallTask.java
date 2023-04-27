package com.medeasy.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medeasy.models.Patient;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class PatientApiCallTask extends Task<Object> {
    String bId;
    String dob;

    public PatientApiCallTask(String bId, String dob) {
        this.bId = bId;
        this.dob = dob;
    }

    @Override
    protected Object call() throws Exception {
        return getPatientInfoFromApi(bId, dob);
    }
    public Object getPatientInfoFromApi(String bId, String dob){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer ( ) ;
        try {
            URL url = new URL("https://bidapi.airamtafir.workers.dev/"+ bId+ "/"+dob);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            int status = 0;
            try {
                status = connection.getResponseCode();
            }catch (IOException e)
            {
                return new String[]{"No Internet Connection", "Please Check your Internet Connectivity"};

            }


            if (status > 299) {
                return new String[]{"Connection Establish Failed", "An Error Occurred While Fetching Details"};

            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                JsonNode node = new ObjectMapper().readTree(responseContent.toString()).get("aaData");
                Iterator<JsonNode> it = node.iterator();
                JsonNode data=null;
                if(it.hasNext())
                {
                    data = it.next();
                }
                if(data!=null) {

                    Patient patient = new Patient(bId,data.get("personNameBn").textValue(),data.get("personNameEn").textValue(), data.get("personBirthDate").textValue(), data.get("fatherNameBn").textValue(),data.get("fatherNameEn").textValue(), data.get("motherNameBn").textValue(),data.get("motherNameEn").textValue(), data.get("fullGeolocationAddressBn").textValue(), data.get("fullGeolocationAddressEn").textValue(), data.get("officeNameBn").textValue(), data.get("officeNameEn").textValue());
                    return patient;
                }
                else
                {
                    return null;
                }

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


        return null;
    }

}
