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
	public void testEmptyRun() throws Exception {
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


}
