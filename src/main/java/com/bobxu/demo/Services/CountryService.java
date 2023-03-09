package com.bobxu.demo.Services;

import com.bobxu.demo.Entities.Country;
import com.bobxu.demo.Repositories.CountryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
public class CountryService {
    @Autowired
    CountryRepo repo;

    @PersistenceContext
    EntityManager entityManager;

    /*
    * JPA methods
    */
    public Country addCountryByJPA(Country country) {
        repo.save(country);
        return country;
    }

    public List<Country> getAllCountriesByJPA() {
        return repo.findAll();
    }

    public Optional<Country>  getCountryByIDByJPA(int id) {
        return repo.findById(id);

    }

    public Country getCountryByName(String name) {
        List<Country> countries = repo.findAll();
        for (Country c : countries) {
            if (c.getName().equalsIgnoreCase(name))
                return c;
        }
        return null;
    }

    public Country updateCountryByJPA(Country country) {
        repo.save(country);
        return country;
    }

    public void deleteCountryByJPA(int id) {
        repo.deleteById(id);
    }

    /*
     * EntityManager methods
     */
    @Transactional
    public void addCountryByEntityManager(Country country){
        entityManager.persist(country);
    }

    public List<Country> getAllCountriesByEntityManager() {
        return entityManager
                //.createQuery("FROM Country",Country.class)
                .createNativeQuery("SELECT * FROM Country",Country.class)
                .getResultList();
    }
    public Country getCountryByIDByEntityManager(int id) {
        Country country = entityManager.find(Country.class, id);
        return country;
    }
    @Transactional
    public Country updateCountryByEntityManager(Country country) {
        return entityManager.merge(country);
    }

    @Transactional
    public void deleteCountryByEntityManager(int id) {
        entityManager.remove(this.getCountryByIDByEntityManager(id));
    }

}
