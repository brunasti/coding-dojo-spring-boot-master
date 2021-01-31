package com.assignment.spring.brunasti.converter;

import com.assignment.spring.brunasti.model.WeatherEntity;
import com.assignment.spring.brunasti.rest.resources.Main;
import com.assignment.spring.brunasti.rest.resources.Sys;
import com.assignment.spring.brunasti.rest.resources.WeatherResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WeatherResponseToEntityTests {

    @Test
    public void mapper_dumb() {
        WeatherResponseToEntity weatherResponseToEntity = new WeatherResponseToEntity();
        
        WeatherResponse weatherResponse = new WeatherResponse();

        assertThrows(NullPointerException.class, () -> {
                weatherResponseToEntity.mapper(weatherResponse);
        });
    }

    @Test
    public void mapper_dumb_2() {
        WeatherResponseToEntity weatherResponseToEntity = new WeatherResponseToEntity();

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setSys(new Sys());

        assertThrows(NullPointerException.class, () -> {
            weatherResponseToEntity.mapper(weatherResponse);
        });
    }

    @Test
    public void mapper_empty() {
        WeatherResponseToEntity weatherResponseToEntity = new WeatherResponseToEntity();

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setSys(new Sys());
        weatherResponse.setMain(new Main());
        WeatherEntity weatherEntity = weatherResponseToEntity.mapper(weatherResponse);

        assertNotNull(weatherEntity);
    }

    @Test
    public void mapper_values() {
        WeatherResponseToEntity weatherResponseToEntity = new WeatherResponseToEntity();

        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setSys(new Sys());
        weatherResponse.setMain(new Main());
        weatherResponse.setName("name");
        weatherResponse.getSys().setCountry("country");
        weatherResponse.getMain().setTemp(0d);
        WeatherEntity weatherEntity = weatherResponseToEntity.mapper(weatherResponse);

        assertNotNull(weatherEntity);
        assertNotNull(weatherEntity.getCity());
        assertNotNull(weatherEntity.getCountry());
        assertNotNull(weatherEntity.getTemperature());
        assertNull(weatherEntity.getId());
    }

}
