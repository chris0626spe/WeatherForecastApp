package com.weiyi.weather.model;

import java.time.LocalDate;

public class WeatherForecast {
    private LocalDate date;
    private double temperature;
    private String description;

    public WeatherForecast(LocalDate date, double temperature, String description) {
        this.date = date;
        this.temperature = temperature;
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("%s: %.1f°C, %s", date, temperature, description);
    }
}

