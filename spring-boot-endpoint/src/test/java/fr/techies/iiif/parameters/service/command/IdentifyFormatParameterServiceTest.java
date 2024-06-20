package fr.techies.iiif.parameters.service.command;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fr.techies.iiif.api.imageapi.services.command.magick.IdentifyFormatParameterService;

@SpringBootTest
public class IdentifyFormatParameterServiceTest {

	@Autowired
	private IdentifyFormatParameterService identifyFormatParameterService;

	@Test
	public void testFormat() {
		System.out.println(this.identifyFormatParameterService.build());
	}
}
