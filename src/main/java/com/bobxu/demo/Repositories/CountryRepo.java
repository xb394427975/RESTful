package com.bobxu.demo.Repositories;

import com.bobxu.demo.Entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepo extends JpaRepository<Country, Integer> {

}
