package com.bobxu.demo;

import com.bobxu.demo.Controllers.CountryController;
import com.bobxu.demo.Entities.Country;
import com.bobxu.demo.Services.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
@SpringBootTest
public class ControllerTests {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    private List<Country> countries;

    public ControllerTests() {
        countries = new ArrayList<>();
        countries.add(new Country(1, "china", "CN"));
        countries.add(new Country(2, "australia", "AU"));
    }

    @Test
    void test_getAllCountries() {
        when(countryService.getAllCountriesByJPA()).thenReturn(countries);
        ResponseEntity<List<Country>> allCountries = countryController.getAllCountries();
        assertEquals(allCountries.getBody().size(),2);
    }

    @Test
    void test_getCountryById(){
        when(countryService.getCountryByIDByJPA(1)).thenReturn(Optional.of(countries.get(0)));
        ResponseEntity<Country> responseEntity = countryController.getCountryById(1);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.FOUND);
        assertEquals(1,responseEntity.getBody().getId());
    }
}
