package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidImageRequestException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidRegionException;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.RegionEnum;

public class RegionValidatorTest extends AbstractImageRequestValidatorTest {
	
	/**
	 * Test du validate Region
	 * 
	 * @throws InvalidImageRequestException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	@Test
	public void validateRegionTest() throws InvalidImageRequestException, NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		// Récupération de la méthode private
		ImageRequest result = null;
		
		// Parametre à tester
		String region = null;
		
		// Autres parametres (valeurs de base)
		String identifier = "";
		String size = "full";
		String rotation = "0";
		String quality = "default";
		String format = "jpg";
		
		/*
		 * TEST DES CAS VALIDES
		 */
		
		// full
		region = "full";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRegion() != null);
		assertEquals(result.getRegion().getRegionEnum(), RegionEnum.full);
		assertEquals(result.getRegion().getRegionPixel(), null);
		assertEquals(result.getRegion().getRegionPCT(), null);
		
		// square
		region = "square";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRegion() != null);
		assertEquals(result.getRegion().getRegionEnum(), RegionEnum.square);
		assertEquals(result.getRegion().getRegionPixel(), null);
		assertEquals(result.getRegion().getRegionPCT(), null);
		
		// x,y,w,h
		region = "50,100,800,500";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRegion() != null);
		assertEquals(result.getRegion().getRegionEnum(), RegionEnum.pixel);
		assertEquals(result.getRegion().getRegionPixel().getPixelX(), 50);
		assertEquals(result.getRegion().getRegionPixel().getPixelY(), 100);
		assertEquals(result.getRegion().getRegionPixel().getPixelW(), 800);
		assertEquals(result.getRegion().getRegionPixel().getPixelH(), 500);
		assertEquals(result.getRegion().getRegionPCT(), null);
		
		// pct:x,y,w,h
		region = "pct:50,10,90,80";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRegion() != null);
		assertEquals(result.getRegion().getRegionEnum(), RegionEnum.pct);
		assertEquals(result.getRegion().getRegionPixel(), null);
		assertEquals(result.getRegion().getRegionPCT().getPctX(), 50.0);
		assertEquals(result.getRegion().getRegionPCT().getPctY(), 10.0);
		assertEquals(result.getRegion().getRegionPCT().getPctW(), 90.0);
		assertEquals(result.getRegion().getRegionPCT().getPctH(), 80.0);
		
		// pct:x,y,w,h
		region = "pct:50.5,10.8,120,110.3";
		result = validatorClass.validateParameters(identifier, region, size, rotation, quality, format);
		assertTrue(result.getRegion() != null);
		assertEquals(result.getRegion().getRegionEnum(), RegionEnum.pct);
		assertEquals(result.getRegion().getRegionPixel(), null);
		assertEquals(result.getRegion().getRegionPCT().getPctX(), 50.5);
		assertEquals(result.getRegion().getRegionPCT().getPctY(), 10.8);
		assertEquals(result.getRegion().getRegionPCT().getPctW(), 120.0);
		assertEquals(result.getRegion().getRegionPCT().getPctH(), 110.3);
	
		/*
		 * TEST DES CAS NON VALIDES
		 */
		List<String> errorRegions = new ArrayList<>();
		errorRegions.add("");
		errorRegions.add("fulllll");
		errorRegions.add("full^");
		errorRegions.add("^full");
		errorRegions.add("max");
		errorRegions.add("squaree");
		errorRegions.add("^square");
		errorRegions.add("10,10,10,");
		errorRegions.add("30,20");
		errorRegions.add("^10,10,10,10");
		errorRegions.add("10,");
		errorRegions.add(",30");
		errorRegions.add("pct:10,30");
		errorRegions.add("^pct:20,30,50,90");
		
		for(String errorRegion : errorRegions) {
			
			Exception exception = assertThrows(InvalidRegionException.class, () -> {
				validatorClass.validateParameters(identifier, errorRegion, size, rotation, quality, format);
			});
			
			assertTrue(exception instanceof InvalidImageRequestException);
			assertTrue(exception instanceof InvalidRegionException);
		}
	}
}
