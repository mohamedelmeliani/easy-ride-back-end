package com.example;

import com.example.dao.UtilisateurRepo;
import com.example.entities.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EasyRideApplication implements CommandLineRunner {
	@Autowired
	UtilisateurRepo repo;
	public static void main(String[] args) {
		SpringApplication.run(EasyRideApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*repo.save(new Utilisateur())*/
	}
}
