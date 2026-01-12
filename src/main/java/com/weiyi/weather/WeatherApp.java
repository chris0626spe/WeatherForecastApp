package com.weiyi.weather;

import com.weiyi.weather.model.WeatherForecast;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.util.List;

public class WeatherApp extends Application {
    private static ConfigurableApplicationContext springContext;
    private WeatherApi weatherApi;
    private WeatherResponseParser parser;
    private TextField cityField;
    private Button searchButton;
    private TextArea resultArea;
    private Label statusLabel;
    private ProgressIndicator progressIndicator;

    public static void setSpringContext(ConfigurableApplicationContext context) {
        springContext = context;
    }

    @Override
    public void init() {
        if (springContext != null) {
            weatherApi = springContext.getBean(WeatherApi.class);
            parser = springContext.getBean(WeatherResponseParser.class);
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Weather Forecast Application");

        // Create UI components
        Label titleLabel = new Label("Weather Forecast");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label cityLabel = new Label("City:");
        cityField = new TextField();
        cityField.setPromptText("Enter city name...");
        cityField.setPrefWidth(300);

        searchButton = new Button("Get Forecast");
        searchButton.setPrefWidth(150);
        searchButton.setStyle("-fx-font-size: 14px; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        searchButton.setOnAction(e -> handleSearch());

        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);

        resultArea = new TextArea();
        resultArea.setEditable(false);
        resultArea.setWrapText(true);
        resultArea.setPrefRowCount(15);
        resultArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12px;");

        statusLabel = new Label("Enter a city name to get weather forecast");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");

        // Layout
        HBox searchBox = new HBox(10);
        searchBox.setAlignment(Pos.CENTER);
        searchBox.getChildren().addAll(cityLabel, cityField, searchButton, progressIndicator);

        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().addAll(titleLabel, searchBox, statusLabel, resultArea);

        // Set up Enter key to trigger search
        cityField.setOnAction(e -> handleSearch());

        Scene scene = new Scene(root, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(600);
        primaryStage.setMinHeight(400);
        
        // Center the window before showing (helps avoid macOS issues)
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void handleSearch() {
        String city = cityField.getText().trim();
        if (city.isEmpty()) {
            statusLabel.setText("Please enter a city name");
            statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff0000;");
            return;
        }

        // Disable button and show progress
        searchButton.setDisable(true);
        progressIndicator.setVisible(true);
        statusLabel.setText("Loading weather forecast for " + city + "...");
        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666;");
        resultArea.clear();

        // Run API call in background thread
        new Thread(() -> {
            try {
                String forecastString = weatherApi.getForecast(city);
                List<WeatherForecast> forecasts = parser.parse(forecastString);
                String errorMessage = parser.getErrorMessage(forecastString);

                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    searchButton.setDisable(false);

                    if (!forecasts.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("Weather Forecast for ").append(city).append("\n");
                        sb.append("=".repeat(50)).append("\n\n");
                        for (WeatherForecast forecast : forecasts) {
                            sb.append(forecast.toString()).append("\n");
                        }
                        resultArea.setText(sb.toString());
                        statusLabel.setText("Forecast loaded successfully");
                        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #4CAF50;");
                    } else if (errorMessage != null) {
                        resultArea.setText("Error: " + errorMessage);
                        statusLabel.setText("Error loading forecast");
                        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff0000;");
                    } else {
                        resultArea.setText("No forecast data available");
                        statusLabel.setText("No data found");
                        statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff0000;");
                    }
                });
            } catch (IOException e) {
                Platform.runLater(() -> {
                    progressIndicator.setVisible(false);
                    searchButton.setDisable(false);
                    resultArea.setText("Error: Failed to fetch weather data. " + e.getMessage());
                    statusLabel.setText("Error loading forecast");
                    statusLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #ff0000;");
                });
            }
        }).start();
    }

    @Override
    public void stop() {
        if (springContext != null) {
            springContext.close();
        }
        Platform.exit();
    }
}
