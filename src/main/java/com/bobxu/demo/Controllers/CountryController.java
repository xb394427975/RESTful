package com.bobxu.demo.Controllers;

import com.bobxu.demo.Entities.Country;
import com.bobxu.demo.Exceptions.CountryFieldNotRequiredException;
import com.bobxu.demo.Exceptions.CountryNotFoundException;
import com.bobxu.demo.Services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@RestController
public class CountryController {
    @Autowired
    CountryService countryService;

    @GetMapping("/countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        return new ResponseEntity(countryService.getAllCountriesByJPA(),HttpStatus.OK);
       // return new ResponseEntity(countryService.getAllCountriesByEntityManager(),HttpStatus.OK);
    }

    @GetMapping("/countries/{id}")
    public ResponseEntity<Country> getCountryById(@PathVariable int id) throws CountryNotFoundException{
        Country country = countryService.getCountryByIDByEntityManager(id);
   //     Country country = countryService.getCountryByIDByJPA(id);
        Optional<Country> o = Optional.ofNullable(country);

            if (o.isPresent())
                return new ResponseEntity(country, HttpStatus.FOUND);
            else{
                throw new CountryNotFoundException("country is not found id:" + id);
            }
    }

    @GetMapping("/countries/countryname")
    public ResponseEntity<Country> getCountryByName(@RequestParam String name) {
        Country country = countryService.getCountryByName(name);
        Optional<Country> o = Optional.ofNullable(country);
        if (o.isPresent())
            return new ResponseEntity(country, HttpStatus.FOUND);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/countries")
    public void addCountry(@RequestBody Country country) {
        countryService.addCountryByEntityManager(country);
     //   return new ResponseEntity(countryService.addCountry(country),HttpStatus.CREATED);
    }

    @PutMapping("/countries/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable int id, @RequestBody Country country) {
        Optional<Country> currentCountry = countryService.getCountryByIDByJPA(id);
        if (currentCountry.isPresent()) {
            if(country.getName() == null || country.getName().isEmpty())
                throw new CountryFieldNotRequiredException("name field is required.");
            else {
                country.setId(currentCountry.get().getId());
                return new ResponseEntity(countryService.updateCountryByEntityManager(country), HttpStatus.OK);
            }
        }else
            throw new CountryNotFoundException("country is not found id:" + id);
    }

    @DeleteMapping("/countries/{id}")
    public ResponseEntity<?> deleteCountryById(@PathVariable int id) {
        countryService.deleteCountryByJPA(id);
        return ResponseEntity.ok().lastModified(System.currentTimeMillis()).body(new Timestamp(System.currentTimeMillis()));
    }
}
