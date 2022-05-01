package com.example;

import com.example.dao.UtilisateurRepo;
import com.example.entities.Role;
import com.example.entities.Utilisateur;
import com.example.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

@SpringBootApplication
public class EasyRideApplication  {
	@Autowired
	UtilisateurRepo repo;
	public static void main(String[] args) {
		SpringApplication.run(EasyRideApplication.class, args);
	}
	@Bean
	CommandLineRunner start (UtilisateurService service){
		return args -> {

				service.addNewRole(new Role("USER"));
				service.addNewRole(new Role("ADMIN"));
				service.addNewUser(new Utilisateur("mohamed", LocalDate.of(2001,7,11)
						,"test","test","test","test","email1","test",false));
				service.addNewUser(new Utilisateur("mohamed", LocalDate.of(2001,7,11)
						,"test","test","test","test","email2","test",false));
				service.addRoleToUser("email1","USER");
				service.addRoleToUser("email1","ADMIN");
				service.addRoleToUser("email2","ADMIN");
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

