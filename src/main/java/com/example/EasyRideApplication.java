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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;


@SpringBootApplication
public class EasyRideApplication  implements WebMvcConfigurer{
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
				/*service.addRoleToUser("email1","USER");
				service.addRoleToUser("email2","USER");*/
				service.addRoleToUser("email1","ADMIN");
		};
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins(
						"http://localhost:4200"
				)
				.allowedMethods(
						"GET",
						"PUT",
						"POST",
						"DELETE",
						"PATCH",
						"OPTIONS"
				);
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}

