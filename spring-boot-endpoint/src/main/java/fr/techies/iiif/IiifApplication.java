package fr.techies.iiif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Attention: spring-boot ne scanne que les classes du même package et en dessous.
 */
@SpringBootApplication
public class IiifApplication {

	public static void main(String[] args) {
		SpringApplication.run(IiifApplication.class, args);
	}

	
}
