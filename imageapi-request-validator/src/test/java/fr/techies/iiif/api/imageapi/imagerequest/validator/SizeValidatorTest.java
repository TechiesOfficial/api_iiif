package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidImageRequestException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidSizeException;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.SizeEnum;

public class SizeValidatorTest extends AbstractImageRequestValidatorTest {

	/**
	 * Test du validate Size
	 *
	 * @throws InvalidImageRequestException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@Test
	public void validateSizeTest() throws InvalidImageRequestException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		// Récupération de la méthode private
		ImageRequest result = null;

		// Parametre à tester
		String size = null;

		// Autres parametres (valeurs de base)
		String identifier = "";
		String region = "full";
		String rotation = "0";
		String quality = "default";
		String format = "jpg";

		/*
		 * TEST DES CAS VALIDES
		 */

		// full
		size = "full";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getSize() != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.full);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		// max
		size = "max";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.max);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		// ^max
		size = "^max";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.max);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		// w,h
		size = "500,300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		// ^w,h
		size = "^500,300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		// !w,h
		size = "!500,300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), true);

		// ^!w,h
		size = "^!500,300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), true);

		// w,
		size = "500,";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), -1);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		// ^w,
		size = "^500,";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), 500);
		assertEquals(result.getSize().getSizePixel().getPixelH(), -1);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		// ,h
		size = ",300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), -1);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		//  ^,h
		size = "^,300";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pixel);
		assertEquals(result.getSize().getSizePixel().getPixelW(), -1);
		assertEquals(result.getSize().getSizePixel().getPixelH(), 300);
		assertEquals(result.getSize().getSizePCT(), null);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		// pct:n
		size = "pct:100";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pct);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT().getPct(), 100.0);
		assertEquals(result.getSize().isAllowUpscaling(), false);
		assertEquals(result.getSize().isKeepRatio(), false);

		//^pct:n
		size = "^pct:120";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pct);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT().getPct(), 120.0);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		//^pct:n.a
		size = "^pct:120.123";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result != null);
		assertEquals(result.getSize().getSizeEnum(), SizeEnum.pct);
		assertEquals(result.getSize().getSizePixel(), null);
		assertEquals(result.getSize().getSizePCT().getPct(), 120.123);
		assertEquals(result.getSize().isAllowUpscaling(), true);
		assertEquals(result.getSize().isKeepRatio(), false);

		/*
		 * TEST DES CAS NON VALIDES
		 */
		List<String> errorSizes = new ArrayList<>();
		errorSizes.add("");
		errorSizes.add("fulllll");
		errorSizes.add("full^");
		errorSizes.add("^full");
		errorSizes.add("maxx");
		errorSizes.add("max^");
		errorSizes.add("a,b");
		errorSizes.add("a,");
		errorSizes.add(",b");
		errorSizes.add("!200,");
		errorSizes.add("!,400");
		errorSizes.add("pct");
		errorSizes.add("pct:");
		errorSizes.add("!pct:100");
		errorSizes.add("pct:^100");
		errorSizes.add("!^200,400");
		errorSizes.add("200,400!");
		errorSizes.add("200,400^");
		errorSizes.add("200,400^!");

		for(String errorSize : errorSizes) {

			Exception exception = assertThrows(InvalidSizeException.class, () -> {
				validatorClass.validateParameters(identifier, region, errorSize, rotation, quality, format);
			});

			assertTrue(exception instanceof InvalidImageRequestException);
			assertTrue(exception instanceof InvalidSizeException);
		}
	}
}
