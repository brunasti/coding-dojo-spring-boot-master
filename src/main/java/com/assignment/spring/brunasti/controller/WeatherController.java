package com.assignment.spring.brunasti.controller;

import com.assignment.spring.brunasti.businessLogic.WeatherBusinessLogic;
import com.assignment.spring.brunasti.model.WeatherEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class WeatherController {

    private final WeatherBusinessLogic weatherBusinessLogic;

    WeatherController(WeatherBusinessLogic weatherBusinessLogic) {
        this.weatherBusinessLogic = weatherBusinessLogic;
    }

    @GetMapping("/weather")
    @ResponseStatus(HttpStatus.OK)
    public WeatherEntity weather(@RequestParam String city) {
        return weatherBusinessLogic.retrieveWeatherFromProvider(city);
    }


    @GetMapping("/weather_all")
    @ResponseStatus(HttpStatus.OK)
    public List<WeatherEntity> weather_all() {
        return weatherBusinessLogic.retrieveAllWeatherFromDB();
    }

}
