package com.assignment.spring.brunasti;

import com.assignment.spring.brunasti.model.WeatherEntity;
import com.assignment.spring.brunasti.repository.WeatherRepository;
import com.assignment.spring.brunasti.rest.resources.WeatherResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class BrunastiApplicationTests {

	ObjectMapper om = new ObjectMapper();

	@Autowired
	WeatherRepository weatherRepository;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void setup() {
		weatherRepository.deleteAll();
	}


	@Test
	public void testGetWeather() throws Exception {
		WeatherEntity weatherEntity = om.readValue(mockMvc.perform(get("/weather?city=amsterdam")
				)
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), WeatherEntity.class);
		assertNotNull(weatherEntity);
		assertEquals("Amsterdam", weatherEntity.getCity());
		assertEquals("NL", weatherEntity.getCountry());
		assertNotNull(weatherEntity.getTemperature());
		assertNotNull(weatherEntity.getId());
		assertTrue(weatherEntity.getId() > 0);
	}

	@Test
	public void testGetWeather_ambiguous() throws Exception {
		WeatherEntity weatherEntity = om.readValue(mockMvc.perform(get("/weather?city=roma")
				)
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), WeatherEntity.class);
		assertNotNull(weatherEntity);
		assertEquals("Rome", weatherEntity.getCity());
		assertEquals("US", weatherEntity.getCountry());

		weatherEntity = om.readValue(mockMvc.perform(get("/weather?city=roma,it")
				)
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), WeatherEntity.class);
		assertNotNull(weatherEntity);
		assertEquals("Rome", weatherEntity.getCity()); // THIS IS WRONG, SHOULD BE ROMA...
		assertEquals("IT", weatherEntity.getCountry());
	}

	@Test
	public void testGetWeather_NonExistingCity() throws Exception {
		mockMvc.perform(get("/weather?city=cippalippa")
				)
				.andDo(print())
				.andExpect(status().isNotFound());
	}

	@Test
	public void testEmptyRun() {
	}

	@Test
	public void testNonExistingURL() throws Exception {
		mockMvc.perform(get("/wrongUrl/"))
				.andExpect(status().isNotFound());
	}


	@Test
	public void testNotAllowedMethod() throws Exception {
		checkUrlNonGet("/weather");
		checkUrlNonGet("/weather_all");
	}

	private void checkUrlNonGet(String url) throws Exception {
		mockMvc.perform(put(url))
				.andExpect(status().isMethodNotAllowed());

		mockMvc.perform(post(url))
				.andExpect(status().isMethodNotAllowed());

		mockMvc.perform(patch(url))
				.andExpect(status().isMethodNotAllowed());
	}



	@Test
	public void testGetAllWeather() throws Exception {
		ArrayList<WeatherEntity> weatherEntities = om.readValue(mockMvc.perform(get("/weather_all")
		)
				.andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString(), ArrayList.class);

		assertNotNull(weatherEntities);
	}

}
