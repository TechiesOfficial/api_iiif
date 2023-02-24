package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;

public class FormatValidatorTest extends AbstractImageRequestValidatorTest {
	
	/**
	 * Test du validate Format
	 * 
	 * @throws InvalidImageRequestException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void validateFormatTest() throws InvalidImageRequestException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Récupération de la méthode private
		ImageRequest result = null;
		
		// Parametre à tester
		String format = null;
		
		// Autres parametres (valeurs de base)
		String identifier = "";
		String size = "full";
		String region = "full";
		String rotation = "0";
		String quality = "default";
		
		/*
		 * TEST DES CAS VALIDES
		 */
		
		format = "jpg";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.jpg);
		
		format = "tif";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.tif);
		
		format = "png";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.png);
		
		format = "gif";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.gif);
		
		format = "jp2";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.jp2);
		
		format = "pdf";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.pdf);
		
		format = "webp";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getFormat() != null);
		assertEquals(result.getFormat().getFormat(), FormatEnum.webp);
		
		/*
		 * TEST DES CAS NON VALIDES
		 */
		List<String> errorFormats = new ArrayList<>();
		errorFormats.add("");
		errorFormats.add("full");
		errorFormats.add("tiff");
		errorFormats.add("jpeg");
		errorFormats.add("jpeg2000");
		errorFormats.add("giff");
		
		for(String errorFormat : errorFormats) {
			
			Exception exception = assertThrows(InvalidFormatException.class, () -> {
				validatorClass.validateParameters(identifier, region, size, rotation, quality, errorFormat);
			});
			
			assertTrue(exception instanceof InvalidImageRequestException);
			assertTrue(exception instanceof InvalidFormatException);
		}
	}
}
