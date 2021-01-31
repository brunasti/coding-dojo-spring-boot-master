package com.assignment.spring.brunasti.controller;

import com.assignment.spring.brunasti.Constants;
import com.assignment.spring.brunasti.converter.WeatherResponseToEntity;
import com.assignment.spring.brunasti.model.WeatherEntity;
import com.assignment.spring.brunasti.repository.WeatherRepository;
import com.assignment.spring.brunasti.rest.resources.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
@Slf4j
public class WeatherController {

    private final WeatherRepository weatherRepository;
    private final WeatherResponseToEntity weatherResponseToEntity;
    private final RestTemplate restTemplate;

    WeatherController(WeatherRepository weatherRepository, WeatherResponseToEntity weatherResponseToEntity, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.weatherResponseToEntity = weatherResponseToEntity;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/weather")
    @ResponseStatus(HttpStatus.OK)
    public WeatherEntity weather(@RequestParam String city) {
        log.info("enter weather city [{}]",city);
        String url = Constants.WEATHER_API_URL.replace("{city}", city).replace("{appid}", Constants.WEATHER_API_KEY);
        log.info("enter weather url [{}]",url);

        ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
        WeatherEntity weatherEntity = weatherResponseToEntity.mapper(response.getBody());
        return weatherRepository.save(weatherEntity);
    }
}
