package fr.techies.iiif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.mappers.MediaTypeMapper;
import fr.techies.iiif.model.RequestsIIIFBean;
import fr.techies.iiif.services.ImageMagickService;

@Controller
public class IiifController {
	
	@Autowired
	private ImageMagickService imageMagickService;

	/**
	 * Point d'entrée des requêtes IIIF.
	 * 
	 * @return
	 */
	@GetMapping("/{id}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<byte[]> displayIIIF(
			@PathVariable String id,
//			@PathVariable int page,
			@PathVariable String region,
			@PathVariable String size,
//			@PathVariable boolean mirroring,
			@PathVariable Double rotation,
			@PathVariable String quality,
			@PathVariable ExtensionEnum format) {
		
		/*
		 * TODO : pour l'instant, seul l'id, la rotation et l'extension sont pris en compte !!
		 * Page non pris en compte !
		 * Pour le reste, il faut mettre des valeurs de base (full etc ..)
		 */
		
		ResponseEntity<byte[]> response = null;
		RequestsIIIFBean iiifRequests = null;
		HttpHeaders header = new HttpHeaders();
		MediaType mediaType = null;
		byte[] image = null;
		
		// Construction du Bean représentant les critères IIIF
		iiifRequests = new RequestsIIIFBean(id, region, size, rotation, quality, format);
		
		// Appel à ImageMagick
		image = imageMagickService.handleImage(iiifRequests);
		
		if(image != null) {
			// Construction du header et don son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(format);
			header.setContentType(mediaType);
			
			// Réponse à retourner
			response = new ResponseEntity<byte[]>(image, header, HttpStatus.OK);
		}
		else {
			response = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
		}
		
		return response;
	}
}
