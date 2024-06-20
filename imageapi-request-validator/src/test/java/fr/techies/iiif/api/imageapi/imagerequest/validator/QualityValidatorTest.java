package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidImageRequestException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidQualityException;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.QualityEnum;

public class QualityValidatorTest extends AbstractImageRequestValidatorTest {

	/**
	 * Test du validate Quality
	 *
	 * @throws InvalidImageRequestException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void validateQualityTest() throws InvalidImageRequestException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Récupération de la méthode private
		ImageRequest result = null;

		// Parametre à tester
		String quality = "default";

		// Autres parametres (valeurs de base)
		String identifier = "";
		String size = "full";
		String region = "full";
		String rotation = "0";
		String format = "jpg";

		/*
		 * TEST DES CAS VALIDES
		 */

		quality = "color";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getQuality() != null);
		assertEquals(result.getQuality().getQualityEnum(), QualityEnum.color);

		quality = "bitonal";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getQuality() != null);
		assertEquals(result.getQuality().getQualityEnum(), QualityEnum.bitonal);

		quality = "gray";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getQuality() != null);
		assertEquals(result.getQuality().getQualityEnum(), QualityEnum.gray);

		quality = "default";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getQuality() != null);
		assertEquals(result.getQuality().getQualityEnum(), QualityEnum.native_default);

		/*
		 * TEST DES CAS NON VALIDES
		 */
		List<String> errorQualities = new ArrayList<>();
		errorQualities.add("");
		errorQualities.add("full");
		errorQualities.add("native");
		errorQualities.add("defaultt");

		for(String errorQuality : errorQualities) {

			Exception exception = assertThrows(InvalidQualityException.class, () -> {
				validatorClass.validateParameters(identifier, region, size, rotation, errorQuality, format);
			});

			assertTrue(exception instanceof InvalidImageRequestException);
			assertTrue(exception instanceof InvalidQualityException);
		}
	}
}
