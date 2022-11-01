package fr.techies.iiif.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.IIIFRequestParametersValidator;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.mappers.MediaTypeMapper;
import fr.techies.iiif.model.RequestsIIIFBean;
import fr.techies.iiif.services.ImageMagickService;

@Controller
public class IiifController {

	@Autowired
	private IIIFRequestParametersValidator iiifRequestParametersValidator;

	@Autowired
	private ImageMagickService imageMagickService;

	/**
	 * Point d'entrée des requêtes IIIF.
	 * 
	 * <p>
	 * NRO: Ne pas changer les types dans leur type définitif (long, double).
	 * </p>
	 * <p>
	 * Afin d'afficher le maximum d'informations à l'utilisateur, il est préférable
	 * de garder des types {@link String} et de faire le contrôle manuellement de
	 * chaque champ de la requête.
	 * </p>
	 * 
	 * @return {@link ResponseEntity} de type byte (données brut image).
	 */
	@GetMapping("/{id}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> displayIIIF(@PathVariable String id, @PathVariable String region,
			@PathVariable String size, @PathVariable String rotation, @PathVariable String quality,
			@PathVariable String format) {

		List<String> errors = null;
		ResponseEntity<?> response = null;
		RequestsIIIFBean iiifRequests = null;
		HttpHeaders header = new HttpHeaders();
		MediaType mediaType = null;
		byte[] image = null;

		errors = this.iiifRequestParametersValidator.validateParameters(id, region, size, rotation, quality, format);
		if(!errors.isEmpty()) {
			
			StringBuilder sb = new StringBuilder("<html><body>");
			for (String error : errors) {
				
				sb.append("<ul>" + error + "</ul>");
			}
			
			response = new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
			return response;
		}
			
		
		// Construction du Bean représentant les critères IIIF
		iiifRequests = new RequestsIIIFBean(id, region, size, rotation, quality, ExtensionEnum.valueOf(format));

		// Appel à ImageMagick
		image = imageMagickService.handleImage(iiifRequests);

		if (image != null) {
			// Construction du header et don son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(ExtensionEnum.valueOf(format));
			header.setContentType(mediaType);

			// Réponse à retourner
			response = new ResponseEntity<byte[]>(image, header, HttpStatus.OK);
		} else {
			response = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}

		return response;
	}

}
