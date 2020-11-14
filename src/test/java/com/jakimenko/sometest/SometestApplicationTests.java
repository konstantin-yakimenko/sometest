package com.jakimenko.sometest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jakimenko.sometest.model.Country;
import com.jakimenko.sometest.repository.ICountryRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TestConfig.class)
@AutoConfigureMockMvc
//@TestPropertySource(locations = "/foo.properties")
class SometestApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ICountryRepo countryRepo;

	@Test
	void getCountry() throws Exception {
		Country country = new Country(1, "United Kingdom");

		mockMvc.perform(get("/api/countries/{id}", 1))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(country)))
				.andExpect(status().isOk());

//		List<Country> countries = countryRepo.getCountries();
//		assertThat(countries.size()).isEqualTo(3);
	}

	@Test
	void saveCountry() throws Exception {
		Country country = new Country();
		country.setName("Russian Federation");
		Country expectResult = new Country(4, "Russian Federation");

		mockMvc.perform(post("/api/countries/", 1)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(country)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
				.andExpect(status().isCreated());
	}

	@Test
	void saveCountryWithId() throws Exception {
		Country expectResult = new Country(7, "France");
		mockMvc.perform(post("/api/countries/", 1)
				.contentType("application/json")
				.content("{\"id\":7,\"name\":\"France\"}"))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(content().json(objectMapper.writeValueAsString(expectResult)))
				.andExpect(status().isCreated());
	}
}
