package com.medeasy.users;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Patient{
    private String name;
    private String dob;
    private String fatherNameBn;
    private String motherNameBn;
    private String addressBn;
    private String addressEn;
    private String officeNameBn;
    private String officeNameEn;

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
    public Patient(String bId,String dob)
    {
        getPatientInfoFromApi(bId,dob);
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

    public static Patient getPatientInfoFromApi(String bId,String dob ){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer ( ) ;
        try {
            URL url = new URL("https://script.google.com/macros/s/AKfycbzUec7v8t3pAVEPAu02GifzPt0VXHN8k3dzjOZXezOSZptsV_1zjvYHG0Cj8Od5aW0h_w/exec?bid="+bId+"&dob="+dob);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(50000);
            connection.setReadTimeout(50000);
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

                JsonNode statusCode = new ObjectMapper().readTree(responseContent.toString()).get("status");
                if(statusCode.asInt()==200) {
                    JsonNode data = new ObjectMapper().readTree(responseContent.toString()).get("data");
                    Patient patient = new Patient(data.get("name").textValue(), data.get("dob").textValue(), data.get("fatherNameBn").textValue(), data.get("motherNameBn").textValue(), data.get("addressBn").textValue(), data.get("addressEn").textValue(), data.get("officeNameBn").textValue(), data.get("officeNameEn").textValue());
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
