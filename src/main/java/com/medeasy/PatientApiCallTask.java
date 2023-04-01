package com.medeasy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.medeasy.users.Patient;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class PatientApiCallTask extends Task<Patient> {
    String bId;
    String dob;

    public PatientApiCallTask(String bId, String dob) {
        this.bId = bId;
        this.dob = dob;
    }

    @Override
    protected Patient call() throws Exception {
        Patient patient= getPatientInfoFromApi(bId, dob);
        System.out.println(patient);

        return patient;
    }
    public Patient getPatientInfoFromApi(String bId, String dob){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer ( ) ;
        try {
            URL url = new URL("https://bidapi.airamtafir.workers.dev/"+ bId+ "/"+dob);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(15000);
            connection.setReadTimeout(15000);
            int status = connection.getResponseCode();

            if (status > 299) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    reader.close();
                }
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();

                //JsonNode statusCode = new ObjectMapper().readTree(responseContent.toString()).get("aaData");
//
                JsonNode node = new ObjectMapper().readTree(responseContent.toString()).get("aaData");
                Iterator<JsonNode> it = node.iterator();
                JsonNode data=null;
                if(it.hasNext())
                {
                    data = it.next();
                }
                if(data!=null) {

                    Patient patient = new Patient(bId,data.get("personNameBn").textValue(), data.get("personBirthDate").textValue(), data.get("fatherNameBn").textValue(), data.get("motherNameBn").textValue(), data.get("fullGeolocationAddressBn").textValue(), data.get("fullGeolocationAddressEn").textValue(), data.get("officeNameBn").textValue(), data.get("officeNameEn").textValue());
                    return patient;
                }
                else
                {
                    return null;
                }

            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();

        }catch (IOException e)
        {
            e.printStackTrace ( ) ;
        }


        return null;
    }

}
