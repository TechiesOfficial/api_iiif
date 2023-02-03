package fr.techies.iiif.api.imageapi.imagerequest.validator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.techies.iiif.api.imageapi.imagerequest.model.Size;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.SizeEnum;

public class ImageRequestParametersValidatorTest {
	
	/**
	 *  Class à tester
	 */
	private ImageRequestParametersValidator validatorClass;
	
	@BeforeEach
	private void beforeAll() {
		validatorClass = new ImageRequestParametersValidator();
		
		ImageRequestParametersValidator.class.getDeclaredMethods();
	}
	
	/**
	 * Permet de récupérer une méthode private par Réflection
	 * 
	 * @param methodeName
	 * @param parameterTypes
	 * @return
	 * @throws NoSuchMethodException
	 */
	private Method getPrivateMethod(String methodeName, Class<?>... parameterTypes) throws NoSuchMethodException {
	    Method method = ImageRequestParametersValidator.class.getDeclaredMethod(methodeName, parameterTypes);
	    method.setAccessible(true);
	    return method;
	}

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
		Method method = this.getPrivateMethod("validateSize", String.class);
		
		Size sizeBean = null;
		String sizeRequest = null;
		
		/*
		 * TEST DES CAS VALIDES
		 */
		
		// full
		sizeRequest = "full";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.full);
		assertEquals(sizeBean.getSizePixel(), null);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// max
		sizeRequest = "max";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.max);
		assertEquals(sizeBean.getSizePixel(), null);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// ^max
		sizeRequest = "^max";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.max);
		assertEquals(sizeBean.getSizePixel(), null);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// w,h
		sizeRequest = "500,300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// ^w,h
		sizeRequest = "^500,300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// !w,h
		sizeRequest = "!500,300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), true);
		
		// ^!w,h
		sizeRequest = "^!500,300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), true);
		
		// w,
		sizeRequest = "500,";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), -1);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// ^w,
		sizeRequest = "^500,";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), 500);
		assertEquals(sizeBean.getSizePixel().getPixelH(), -1);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// ,h
		sizeRequest = ",300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), -1);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		//  ^,h
		sizeRequest = "^,300";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pixel);
		assertEquals(sizeBean.getSizePixel().getPixelW(), -1);
		assertEquals(sizeBean.getSizePixel().getPixelH(), 300);
		assertEquals(sizeBean.getSizePCT(), null);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		// pct:n
		sizeRequest = "pct:100";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pct);
		assertEquals(sizeBean.getSizePixel(), null);
		assertEquals(sizeBean.getSizePCT().getPct(), 100.0);
		assertEquals(sizeBean.isAllowUpscaling(), false);
		assertEquals(sizeBean.isKeepRatio(), false);
		
		//^pct:n
		sizeRequest = "^pct:120";
		sizeBean = (Size) method.invoke(validatorClass, sizeRequest);
		assertTrue(sizeBean != null);
		assertEquals(sizeBean.getSizeEnum(), SizeEnum.pct);
		assertEquals(sizeBean.getSizePixel(), null);
		assertEquals(sizeBean.getSizePCT().getPct(), 120.0);
		assertEquals(sizeBean.isAllowUpscaling(), true);
		assertEquals(sizeBean.isKeepRatio(), false);
	
		/*
		 * TEST DES CAS NON VALIDES
		 */
//		assertThrows(InvalidSizeException.class, method.invoke(validatorClass, "fulllllll"));
	}
}
