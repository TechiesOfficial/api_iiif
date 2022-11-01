package fr.techies.iiif;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

@SpringBootTest
class IiifApplicationTests {

	@Autowired
	ResourcePatternResolver resourceResolver;

	@Test
	void contextLoads() {

		try {
			for (Resource resourcee : resourceResolver.getResources("classpath:*/**")) {

				System.out.println(resourcee.getFilename());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
