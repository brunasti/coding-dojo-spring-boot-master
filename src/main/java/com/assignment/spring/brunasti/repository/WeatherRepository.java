package com.assignment.spring.brunasti.repository;

import com.assignment.spring.brunasti.model.WeatherEntity;
import org.springframework.data.repository.CrudRepository;

public interface WeatherRepository extends CrudRepository<WeatherEntity, Integer> {
}
