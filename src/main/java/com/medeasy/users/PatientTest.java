package com.medeasy.users;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PatientTest {
    private String name;
    private String dob;
    private String fatherNameBn;
    private String motherNameBn;
    private String addressBn;
    private String addressEn;
    private String officeNameBn;
    private String officeNameEn;

    public PatientTest(String name, String dob, String fatherNameBn, String motherNameBn, String addressBn, String addressEn, String officeNameBn, String officeNameEn) {
        this.name = name;
        this.dob = dob;
        this.fatherNameBn = fatherNameBn;
        this.motherNameBn = motherNameBn;
        this.addressBn = addressBn;
        this.addressEn = addressEn;
        this.officeNameBn = officeNameBn;
        this.officeNameEn = officeNameEn;
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

    public static PatientTest getPatientInfoFromApi( ){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer ( ) ;
        try {
            URL url = new URL("http://bdris.gov.bd/api/br/br-projection-list-by-ubrn-and-dob/20025114340020727?personBirthDate=03/10/2002&sEcho=4&iDisplayStart=0&iDisplayLength=-1&iSortCol_0=1&sSortDir_0=desc");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(50000);
            connection.setReadTimeout(50000);
            int status = connection.getResponseCode();
            System.out.println(status);
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

                System.out.println(responseContent.toString());

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

    public static void main(String[] args) {
        PatientTest.getPatientInfoFromApi();

    }
}
