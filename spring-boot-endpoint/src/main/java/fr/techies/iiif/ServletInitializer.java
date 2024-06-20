package fr.techies.iiif;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Allow to run the project as a war file.
 * 
 * The project is <b>NOT</b> intended to be run as an executable file in production.
 */
public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IiifApplication.class);
	}
}
