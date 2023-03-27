package com.medeasy.users;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

public class Patient {
    private String bId;
    private String name;
    private String dob;
    private String fatherNameBn;
    private String motherNameBn;
    private String addressBn;
    private String addressEn;
    private String officeNameBn;
    private String officeNameEn;
    private String username;
    private String email;


    public Patient(String name, String dob, String fatherNameBn, String motherNameBn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.name = name;
        this.dob = dob;
        this.fatherNameBn = fatherNameBn;
        this.motherNameBn = motherNameBn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }
    public Patient(String bId,String name, String dob, String fatherNameBn, String motherNameBn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.name = name;
        this.dob = dob;
        this.bId = bId;
        this.fatherNameBn = fatherNameBn;
        this.motherNameBn = motherNameBn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
    }

    public Patient(Patient patient,String username,String email)

    {
        this.name = patient.name;
        this.bId = patient.bId;
        this.dob = patient.dob;
        this.fatherNameBn = patient.fatherNameBn;
        this.motherNameBn = patient.motherNameBn;
        this.addressBn = patient.addressBn;
        this.addressEn = patient.addressEn;
        this.officeNameBn = patient.officeNameBn;
        this.officeNameEn = patient.officeNameEn;
        this.username = username;
        this.email = email;

    }

    public String getbId() {
        return bId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPatientName() {
        return name;
    }

    public void setPatientName(String name) {
        this.name = name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFatherNameBn() {
        return fatherNameBn;
    }

    public void setFatherNameBn(String fatherNameBn) {
        this.fatherNameBn = fatherNameBn;
    }

    public String getMotherNameBn() {
        return motherNameBn;
    }

    public void setMotherNameBn(String motherNameBn) {
        this.motherNameBn = motherNameBn;
    }

    public String getAddressBn() {
        return addressBn;
    }

    public void setAddressBn(String addressBn) {
        this.addressBn = addressBn;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getOfficeNameBn() {
        return officeNameBn;
    }

    public void setOfficeNameBn(String officeNameBn) {
        this.officeNameBn = officeNameBn;
    }

    public String getOfficeNameEn() {
        return officeNameEn;
    }

    public void setOfficeNameEn(String officeNameEn) {
        this.officeNameEn = officeNameEn;
    }

    @Override
    public String toString() {
        return "Person [name: "+ name+  " Date Of Birth: " + dob + " Father's Name: " + fatherNameBn + " Mother's Name: "+ motherNameBn;
    }

    public static Patient getPatientInfoFromApi(String bId, String dob){
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
