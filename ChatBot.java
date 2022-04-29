package com.company;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import org.jibble.pircbot.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/*
        OpenWeather API key: 416052b76afffaa1ae757c4d3c7a30f1
 */

//class ChatBot extends Abstract Class "PircBot"
public class ChatBot extends PircBot
{
    String key = "416052b76afffaa1ae757c4d3c7a30f1";
    public ChatBot()
    {
        //set the name of the ChatBot
        this.setName("WeatherBot");
    }

    //Method to connect to the OpenWeather API
    public void connect(String channel, String zipcode)throws IOException
    {
        //get url with entered zipcode
        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?zip=" + zipcode + ",us&appid=" + key);
        url.openConnection();
        //HttpURLConnection object
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        //create GET request using above object
        con.setRequestMethod("GET");
        con.connect();
        //create BufferedReader to read connection input stream
        BufferedReader iStream = new BufferedReader(new InputStreamReader(con.getInputStream()));
        //variable to hold the api call data (as a string)
        String weatherData;
        StringBuffer buffer = new StringBuffer();
        while ((weatherData = iStream.readLine()) != null)
        {
            buffer.append(weatherData);
        }
        //store the JSON response in a string
        String jsonResponse = buffer.toString();
        //create a JSON Element which acts as a parser for the JSON response returned from API call
        JsonElement jElement = new JsonParser().parse(jsonResponse);
        //parse the JSON string
        JsonObject jObject = jElement.getAsJsonObject();
        JsonObject name = jElement.getAsJsonObject();
        jObject = jObject.getAsJsonObject("main");
        JsonPrimitive temp = jObject.getAsJsonPrimitive("temp");

        //output the weather data for the specified zip code
        JsonElement city = name.get("name");
        String cityString = city.getAsString();
        cityString.replace("\"", "");
        sendMessage(channel, cityString + " is " + temp + " degrees.");

        iStream.close();
        con.disconnect();
    }

    /**
     * onMessage - will return message from the api
     * @returns void
     * @param channel - String
     * @param sender - String
     * @param login - String
     * @param hostname - String
     * @param message
     */
    public void onMessage(String channel, String sender, String login, String hostname, String message )
    {
        if(message.toLowerCase().contains("!weather ")){
            String zipCode = message.replace("!weather ", "");
            try
            {
                connect(channel, zipCode);
            } catch (IOException e)
            {
                e.printStackTrace();
                //if unable to connect to API, return error message
                sendMessage(channel, "Could not find this zip code. Try again mate");
            }
        }
    }

}
