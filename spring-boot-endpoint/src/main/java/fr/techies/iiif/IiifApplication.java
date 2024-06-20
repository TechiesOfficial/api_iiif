package fr.techies.iiif;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Attention: spring-boot ne scanne que les classes du même package et en
 * dessous.
 */
@SpringBootApplication
public class IiifApplication {

	public static void main(String[] args) {

		try {
			SpringApplication.run(IiifApplication.class, args);
		} catch (Throwable e) {
			if (e.getClass().getName().contains("SilentExitException")) {
//	            LOGGER.debug("Spring is restarting the main thread - See spring-boot-devtools");
			} else {
//	            LOGGER.error("Application crashed!", e);
			}
		}
	}
}
