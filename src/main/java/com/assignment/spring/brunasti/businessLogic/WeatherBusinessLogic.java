package com.assignment.spring.brunasti.businessLogic;

import com.assignment.spring.brunasti.Constants;
import com.assignment.spring.brunasti.converter.WeatherResponseToEntity;
import com.assignment.spring.brunasti.exception.CityNotFoundException;
import com.assignment.spring.brunasti.model.WeatherEntity;
import com.assignment.spring.brunasti.repository.WeatherRepository;
import com.assignment.spring.brunasti.rest.resources.WeatherResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class WeatherBusinessLogic {

    private final WeatherRepository weatherRepository;
    private final WeatherResponseToEntity weatherResponseToEntity;
    private final RestTemplate restTemplate;

    WeatherBusinessLogic(WeatherRepository weatherRepository, WeatherResponseToEntity weatherResponseToEntity, RestTemplate restTemplate) {
        this.weatherRepository = weatherRepository;
        this.weatherResponseToEntity = weatherResponseToEntity;
        this.restTemplate = restTemplate;
    }

    public WeatherEntity retrieveWeatherFromProvider(String city) {
        log.info("request weather for city [{}]",city);
        String url = Constants.WEATHER_API_URL.replace("{city}", city).replace("{appid}", Constants.WEATHER_API_KEY);
        log.info("openWeather url [{}]",url);

        try {
            ResponseEntity<WeatherResponse> response = restTemplate.getForEntity(url, WeatherResponse.class);
            log.info("openWeather response [{}]", response);
            log.info("openWeather body [{}]", response.getBody());
            WeatherEntity weatherEntity = weatherResponseToEntity.mapper(response.getBody());
            log.info("weatherEntity [{}]", weatherEntity);

            return weatherRepository.save(weatherEntity);
        } catch (HttpClientErrorException clientErrorException) {
            log.error("Exception while calling url : ["+url+"]",clientErrorException);
            throw new CityNotFoundException(city);
        }
    }

    public List<WeatherEntity> retrieveAllWeatherFromDB() {
        log.info("request all weather ");
        ArrayList<WeatherEntity> list = new ArrayList<>();
        weatherRepository.findAll().forEach(weatherEntity -> {list.add(weatherEntity);});
        return list;
    }

}
