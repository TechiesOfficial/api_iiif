package fr.techies.iiif.controller.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.AIIIFRequestManager;
import fr.techies.iiif.common.enums.FormatEnum;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.services.image.register.AutoDiscoverImagesFromPathService;

@Service
public class InformationRequestParametersValidator extends AIIIFRequestManager {

	@Autowired
	private AutoDiscoverImagesFromPathService autoDiscoverImagesFromPathService;
	
	public List<String> validateParameters(String identifier) throws ImageNotFoundException {

		List<String> errors = new ArrayList<String>();
		
		try {
			this.autoDiscoverImagesFromPathService.getPath(identifier).toString();
		} catch (ImageNotFoundException e) {
			throw e;
		}
		
		return errors;
	}

}
