package com.example.dao;

import com.example.entities.Assiste;
import com.example.entities.AssisteKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
@Repository
public interface AssisteRepo extends JpaRepository<Assiste, AssisteKey> {

}
