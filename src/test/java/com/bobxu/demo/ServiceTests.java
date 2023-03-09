package com.bobxu.demo;

import com.bobxu.demo.Entities.Country;
import com.bobxu.demo.Repositories.CountryRepo;
import com.bobxu.demo.Services.CountryService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@SpringBootTest
public class ServiceTests {
    @Mock
    CountryRepo countryRepo;
    @InjectMocks
    CountryService countryService;

    private List<Country> countries;

    public ServiceTests(){
        countries = new ArrayList<>();
        countries.add(new Country(1,"china","CN"));
        countries.add(new Country(2,"australia","AU"));
    }
    @Test
    void test_getAllCountries() {

        when(countryRepo.findAll()).thenReturn(countries);
        List<Country> allCountries = countryService.getAllCountriesByJPA();
        assertEquals(2,allCountries.size());

    }
}
