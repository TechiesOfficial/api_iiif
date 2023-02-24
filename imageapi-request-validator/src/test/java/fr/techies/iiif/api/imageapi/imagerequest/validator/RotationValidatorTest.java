package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;

public class RotationValidatorTest extends AbstractImageRequestValidatorTest {
	
	/**
	 * Test du validate Rotation
	 * 
	 * @throws InvalidImageRequestException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void validateRotationTest() throws InvalidImageRequestException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Récupération de la méthode private
		ImageRequest result = null;
		
		// Parametre à tester
		String rotation = null;
		
		// Autres parametres (valeurs de base)
		String identifier = "";
		String size = "full";
		String region = "full";
		String quality = "default";
		String format = "jpg";
		
		/*
		 * TEST DES CAS VALIDES
		 */
		
		rotation = "0";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRotation() != null);
		assertEquals(result.getRotation().getDegree(), 0);
		assertEquals(result.getRotation().isMirroring(), false);
		
		rotation = "180";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRotation() != null);
		assertEquals(result.getRotation().getDegree(), 180);
		assertEquals(result.getRotation().isMirroring(), false);
		
		rotation = "!180";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRotation() != null);
		assertEquals(result.getRotation().getDegree(), 180);
		assertEquals(result.getRotation().isMirroring(), true);
		
		rotation = "360";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRotation() != null);
		assertEquals(result.getRotation().getDegree(), 360);
		assertEquals(result.getRotation().isMirroring(), false);
		
		/*
		 * TEST DES CAS NON VALIDES
		 */
		List<String> errorRotations = new ArrayList<>();
		errorRotations.add("");
		errorRotations.add("full");
		errorRotations.add("a");
		errorRotations.add("!a");
		errorRotations.add("361");
		
		for(String errorRotation : errorRotations) {
			
			Exception exception = assertThrows(InvalidRotationException.class, () -> {
				validatorClass.validateParameters(identifier, region, size, errorRotation, quality, format);
			});
			
			assertTrue(exception instanceof InvalidImageRequestException);
			assertTrue(exception instanceof InvalidRotationException);
		}
	}
}
