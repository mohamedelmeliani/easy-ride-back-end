package com.example;

import com.example.entities.Assiste;
import com.example.entities.AssisteKey;
import org.json.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.*;

@CrossOrigin("*")
@RestController
public class test {
    @GetMapping(value = "/cities")
    public String cities(){
        try {
            URL url = new URL("https://parseapi.back4app.com/classes/CitiesMorocco_List_of_Morroco_cities?limit=9");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestProperty("X-Parse-Application-Id", "8eRz4rfEDJet9PqKifpsMmOkZLE7QAi3tbXdloeP"); // This is your app's application id
            urlConnection.setRequestProperty("X-Parse-REST-API-Key", "ZryJx8oy0Yt1KnHiF1GCatf6lqVm6EZHwdZmapen"); // This is your app's REST API key
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                JSONObject data = new JSONObject(stringBuilder.toString()); // Here you have the data that you need
                return data.toString(2);
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            return "Error: " + e.toString();
        }
    }
}
