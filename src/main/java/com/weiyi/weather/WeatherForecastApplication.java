package com.weiyi.weather;

import javafx.application.Application;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class WeatherForecastApplication {
    private static ConfigurableApplicationContext applicationContext;

    public static void main(String[] args) {
        // Start Spring Boot context
        applicationContext = SpringApplication.run(WeatherForecastApplication.class, args);
        
        // Set Spring context for JavaFX application
        WeatherApp.setSpringContext(applicationContext);
        
        // Launch JavaFX application on JavaFX Application Thread
        Application.launch(WeatherApp.class, args);
    }

    public static ConfigurableApplicationContext getApplicationContext() {
        return applicationContext;
    }
}

