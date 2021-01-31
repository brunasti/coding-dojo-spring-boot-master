package com.assignment.spring.brunasti.controller;

import com.assignment.spring.brunasti.businessLogic.WeatherBusinessLogic;
import com.assignment.spring.brunasti.model.WeatherEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
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
}
