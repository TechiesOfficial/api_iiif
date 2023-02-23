package fr.techies.iiif.api.imageapi.imagerequest.validator;

import org.junit.jupiter.api.BeforeEach;

public abstract class AbstractImageRequestValidatorTest {
	
	/**
	 *  Class à tester
	 */
	protected ImageRequestParametersValidator validatorClass;
	
	@BeforeEach
	protected void beforeAll() {
		validatorClass = new ImageRequestParametersValidator();
	}
}
