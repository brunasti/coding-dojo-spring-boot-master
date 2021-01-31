package com.assignment.spring.brunasti.converter;

import com.assignment.spring.brunasti.model.WeatherEntity;
import com.assignment.spring.brunasti.rest.resources.WeatherResponse;
import org.springframework.stereotype.Component;

@Component
public class WeatherResponseToEntity {
    public WeatherEntity mapper(WeatherResponse response) {
        WeatherEntity entity = new WeatherEntity();
        entity.setCity(response.getName());
        entity.setCountry(response.getSys().getCountry());
        entity.setTemperature(response.getMain().getTemp());
        return entity;
    }

}
