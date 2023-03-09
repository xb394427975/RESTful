package com.bobxu.demo;

import com.bobxu.demo.Controllers.CountryController;
import com.bobxu.demo.Entities.Country;
import com.bobxu.demo.Services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.bobxu.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest
public class ControllerMVCTest {
    @Autowired
    MockMvc mvc;

    @InjectMocks
    CountryController countryController;

    @Mock
    CountryService countryService;

    private List<Country> countries;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(countryController).build();
        countries = new ArrayList<>();
        countries.add(new Country(1, "china", "CN"));
        countries.add(new Country(2, "australia", "AU"));
    }

    @Test
    public void test_getAllCountries() throws Exception {
        when(countryService.getAllCountriesByJPA()).thenReturn(countries);

        MvcResult mvcResult = mvc.perform(get("/countries"))
                .andExpect(status().isOk())
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        Country[] countries = new ObjectMapper().readValue(content,Country[].class);
        assertTrue(countries.length > 0);
    }

    @Test
    public void test_getCountryByID() throws Exception {
        when(countryService.getCountryByIDByJPA(1)).thenReturn(Optional.of(countries.get(0)));

        mvc.perform(get("/countries/{id}", 1))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("china"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("CN"))
                .andDo(print());
    }

    @Test
    public void test_getCountryByName() throws Exception {
        when(countryService.getCountryByName("china")).thenReturn(countries.get(0));

        mvc.perform(get("/countries/countryname").param("name","china"))
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("china"))
                .andExpect(MockMvcResultMatchers.jsonPath(".capital").value("CN"))
                .andDo(print());
    }

    @Test
    public void test_addCountry() throws Exception {
        Country country = new Country(3,"Germany","GB");
        when(countryService.addCountryByJPA(country)).thenReturn(country);
        String jsonContent = new ObjectMapper().writeValueAsString(country);
        mvc.perform(post("/countries").content(jsonContent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void test_updateCountry() throws Exception {
        Country country = new Country(2,"Germany","GB");
        when(countryService.getCountryByIDByJPA(2)).thenReturn(Optional.of(country));
        when(countryService.updateCountryByJPA(country)).thenReturn(country);
        String jsonContent = new ObjectMapper().writeValueAsString(country);
        mvc.perform(put("/countries/{id}",2).content(jsonContent).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(".name").value("Germany"))
                .andDo(print());
    }
}
