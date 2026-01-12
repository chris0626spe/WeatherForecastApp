# Weather Forecast Application

A desktop weather forecast application built with JavaFX and Spring Boot that fetches and displays weather forecasts for cities worldwide using the OpenWeatherMap API.

## Features

- 🌤️ **Real-time Weather Forecast**: Get 5-day weather forecasts for any city
- 🎨 **Modern JavaFX UI**: Clean and intuitive user interface
- 🔄 **Asynchronous Loading**: Non-blocking API calls with progress indicators
- 📊 **Detailed Information**: Displays date, temperature, and weather conditions
- ✅ **Error Handling**: User-friendly error messages for API failures
- 🚀 **Spring Boot Integration**: Leverages Spring's dependency injection

## Technologies Used

- **Java 17**: Programming language
- **Spring Boot 3.1.5**: Application framework and dependency injection
- **JavaFX 21.0.1**: Desktop application UI framework
- **OpenWeatherMap API**: Weather data provider
- **Maven**: Build tool and dependency management
- **JSON**: Data format for API responses

## Prerequisites

Before running this application, make sure you have the following installed:

- **Java JDK 17** or higher ([Download here](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.6+** ([Download here](https://maven.apache.org/download.cgi))
- **OpenWeatherMap API Key** ([Get free API key here](https://openweathermap.org/api))

## Setup Instructions

### 1. Clone or Download the Project

```bash
cd WeatherForecastApp
```

### 2. Configure API Key

The application uses the OpenWeatherMap API. You can either:

**Option A: Update the API key in the code** (Current setup)
- Edit `src/main/java/com/weiyi/weather/WeatherApi.java`
- Replace the `API_KEY` constant with your own API key

**Option B: Use environment variable or configuration file** (Recommended for production)
- Modify `WeatherApi.java` to read from environment variables or properties file
- Add your API key to `application.properties` or system environment

### 3. Build the Project

```bash
mvn clean install
```

This will compile the project and download all required dependencies.

## Running the Application

### Using Maven

```bash
mvn spring-boot:run
```

### Using Java directly (after building)

```bash
java -jar target/WeatherForecastApp-1.0.0.jar
```

**Note**: Due to JavaFX module requirements, you may need to run with additional JVM arguments. For easier execution, use the Maven Spring Boot plugin as shown above.

## Usage

1. **Launch the Application**: Start the application using one of the methods above
2. **Enter City Name**: Type the name of a city in the text field (e.g., "London", "New York", "Tokyo")
3. **Get Forecast**: Click the "Get Forecast" button or press Enter
4. **View Results**: The weather forecast will be displayed in the text area below, showing:
   - Date
   - Temperature (in Celsius)
   - Weather description

### Example Queries

- City names: `London`, `New York`, `Tokyo`, `Paris`
- City with country code: `London,UK` or `Paris,FR` (may improve accuracy)

## Project Structure

```
WeatherForecastApp/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── weiyi/
│       │           └── weather/
│       │               ├── WeatherForecastApplication.java  # Spring Boot entry point
│       │               ├── WeatherApp.java                  # JavaFX application UI
│       │               ├── WeatherApi.java                  # OpenWeatherMap API client
│       │               ├── WeatherResponseParser.java       # JSON response parser
│       │               └── model/
│       │                   └── WeatherForecast.java         # Data model
│       └── resources/
│           └── application.properties                       # Spring Boot configuration
├── pom.xml                                                  # Maven configuration
└── README.md                                                # This file
```

## Key Components

### WeatherForecastApplication
- Main Spring Boot application class
- Initializes Spring context and launches JavaFX application

### WeatherApp
- JavaFX application with user interface
- Handles user interactions and displays weather data
- Manages asynchronous API calls

### WeatherApi
- Service class for making HTTP requests to OpenWeatherMap API
- Handles URL encoding and API communication

### WeatherResponseParser
- Parses JSON responses from the API
- Converts JSON data into `WeatherForecast` objects
- Handles error messages

### WeatherForecast
- Model class representing a single weather forecast entry
- Contains date, temperature, and description

## Dependencies

- **Spring Boot Starter**: Core Spring Boot functionality
- **JavaFX Controls**: UI components (buttons, labels, text fields)
- **JavaFX FXML**: FXML support (for future enhancements)
- **JSON Library**: JSON parsing and manipulation
- **Spring Boot Test**: Testing framework

## Configuration

Application properties can be customized in `src/main/resources/application.properties`:

```properties
spring.application.name=Weather Forecast Application
```

For logging configuration, you can uncomment and modify:

```properties
logging.level.com.weiyi.weather=DEBUG
```

## API Information

This application uses the [OpenWeatherMap 5 Day / 3 Hour Forecast API](https://openweathermap.org/forecast5).

- **API Endpoint**: `http://api.openweathermap.org/data/2.5/forecast`
- **Units**: Metric (Celsius)
- **Rate Limits**: Free tier includes 60 calls/minute
- **API Key**: Required (get your free key at [openweathermap.org](https://openweathermap.org/api))

## Troubleshooting

### Application won't start
- Ensure Java 17+ is installed and configured
- Check that Maven dependencies are downloaded (`mvn clean install`)
- Verify Spring Boot version compatibility

### API errors
- Verify your API key is correct and active
- Check your internet connection
- Ensure the city name is spelled correctly
- Check OpenWeatherMap API status

### JavaFX runtime errors
- Make sure JavaFX dependencies are properly downloaded
- For Java 11+, JavaFX is not included in JDK - ensure Maven dependencies are resolved
- Try running with: `mvn spring-boot:run` instead of direct Java execution

## Future Enhancements

Potential improvements for future versions:

- [ ] Add unit and integration tests
- [ ] Support for multiple weather APIs
- [ ] Weather icons and charts
- [ ] City search autocomplete
- [ ] Save favorite cities
- [ ] Temperature unit conversion (Celsius/Fahrenheit)
- [ ] Weather alerts and notifications
- [ ] Export forecasts to file

## License

This project is open source and available for educational purposes.

## Contributing

Contributions, issues, and feature requests are welcome!

## Author

Developed as a demonstration of JavaFX and Spring Boot integration for weather forecasting.

---

**Note**: Remember to keep your API key secure and never commit it to public repositories in production applications.

