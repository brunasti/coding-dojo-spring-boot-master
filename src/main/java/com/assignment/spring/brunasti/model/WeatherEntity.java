package com.assignment.spring.brunasti.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "weather")
@Data
public class WeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String city;

    private String country;

    private Double temperature;

}
