package com.weiyi.weather;

import com.weiyi.weather.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherResponseParser {
    public List<WeatherForecast> parse(String jsonResponse) {
        List<WeatherForecast> forecasts = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(jsonResponse);

        if (jsonObject.getInt("cod") == 200) {
            JSONArray forecastArray = jsonObject.getJSONArray("list");
            for (int i = 0; i < forecastArray.length(); i++) {
                JSONObject forecast = forecastArray.getJSONObject(i);
                long timestamp = forecast.getLong("dt");
                LocalDate date = Instant.ofEpochSecond(timestamp)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                double temperature = forecast.getJSONObject("main").getDouble("temp");
                String description = forecast.getJSONArray("weather")
                        .getJSONObject(0).getString("description");

                forecasts.add(new WeatherForecast(date, temperature, description));
            }
        }
        return forecasts;
    }

    public String getErrorMessage(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            if (jsonObject.getInt("cod") != 200) {
                return jsonObject.optString("message", "Request contains an error!");
            }
        } catch (Exception e) {
            return "Error parsing response: " + e.getMessage();
        }
        return null;
    }
}
