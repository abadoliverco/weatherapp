# Weather App

Android App written in Kotlin with MVVM architeture.

## GOALS
- Get the current location
- Display the weather forecast based on user location
- Show hourly forecast
- Show daily forecast
- Show other details

## Tech Stack
- Kotlin
- MVVM Architecture
- Koin
- Retrofit
- Material Design

## API
- I used OpenWeatherMap for the weather forecast API
- https://openweathermap.org/api

## Setup
``` 
// setup your own API KEY 
object Constants {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    const val API_KEY = "YOUR API KEY"
//...
}
```
