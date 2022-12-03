package fr.techies.iiif.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.IIIFRequestParametersValidator;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.controller.ImageAPIController;
import fr.techies.iiif.exception.ImageAPIRequestFormatException;
import fr.techies.iiif.model.RequestsIIIFBean;

/**
 * On separe le controller {@link ImageAPIController} de l'implémentation concrète de l'API.
 * L'objectif est à terme de mettre tout ce qui n'est pas web dans une dépendance et un autre jar.
 */
@Service
public class ImageAPIService {

	@Autowired
	private IIIFRequestParametersValidator iiifRequestParametersValidator;

	@Autowired
	private ImageMagickService imageMagickService;
	
	
	public byte[] getResultingImage(String id, String view, String region, String size, String rotation, String quality,
			String format) throws ImageAPIRequestFormatException {
		
		List<String> errors = null;
		RequestsIIIFBean iiifRequests = null;
		byte[] image = null;

		errors = this.iiifRequestParametersValidator.validateParameters(id, region, size, rotation, quality, format);
		if (!errors.isEmpty()) {

			StringBuilder sb = new StringBuilder("<html><body>");
			for (String error : errors) {

				sb.append("<ul>" + error + "</ul>");
			}

			throw new ImageAPIRequestFormatException(sb.toString());
		}

		// Construction du Bean représentant les critères IIIF
		iiifRequests = new RequestsIIIFBean(id, region, size, rotation, quality, ExtensionEnum.valueOf(format));

		// Appel à ImageMagick
		image = imageMagickService.handleImage(iiifRequests);

		return image;
	}
}
