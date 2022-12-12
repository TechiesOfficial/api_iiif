package fr.techies.iiif.api.imageapi.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.services.image.register.AutoDiscoverImagesFromPathService;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.lib.AIIIFRequestManager;

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
