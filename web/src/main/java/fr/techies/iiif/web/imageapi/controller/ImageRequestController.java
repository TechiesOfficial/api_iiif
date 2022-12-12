package fr.techies.iiif.web.imageapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.api.image.services.ImageRequestService;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.exception.ImageRequestFormatException;
import fr.techies.iiif.lib.enums.FormatEnum;
import fr.techies.iiif.lib.mappers.MediaTypeMapper;

/**
 * On met ici la gestion de vue aussi mais il faudra créér un autre contoller
 * (sous-controller de celui-ci?) activable éventuellement via un profil spring
 * (ou à la compil?) pour les clients voulant un numéro de vue et d'autres non.
 * Appel au point d'entrée sans vue = vue 0?
 */
@Controller
public class ImageRequestController {

	@Autowired
	private ImageRequestService imageAPIService;

	/**
	 * Point d'entrée des requêtes IIIF. Ce point d'entrée comporte une gestion de
	 * vue.
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
	@GetMapping("/{identifier}/{view}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String identifier, @PathVariable String view, @PathVariable String region,
			@PathVariable String size, @PathVariable String rotation, @PathVariable String quality,
			@PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			image = this.getResultingImage(identifier, view, region, size, rotation, quality, format);

			// Construction du header et don son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(FormatEnum.valueOf(format));
			httpHeaders.setContentType(mediaType);

			// Réponse à retourner
			responseEntity = new ResponseEntity<byte[]>(image, httpHeaders, HttpStatus.OK);

		} catch (ImageRequestFormatException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	/**
	 * Point d'entrée des requêtes IIIF. Ce point d'entrée ne comporte pas de
	 * gestion de vue.
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
	@GetMapping("/{identifier}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String identifier, @PathVariable String region, @PathVariable String size,
			@PathVariable String rotation, @PathVariable String quality, @PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			image = this.getResultingImage(identifier, null, region, size, rotation, quality, format);

			// Construction du header et de son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(FormatEnum.valueOf(format));
			httpHeaders.setContentType(mediaType);

			// Réponse à retourner
			responseEntity = new ResponseEntity<byte[]>(image, httpHeaders, HttpStatus.OK);

		} catch (ImageRequestFormatException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

		return responseEntity;
	}

	private byte[] getResultingImage(String identifier, String view, String region, String size, String rotation,
			String quality, String format) throws ImageRequestFormatException, ImageNotFoundException {
		return this.imageAPIService.getResultingImage(identifier, view, region, size, rotation, quality, format);
	}
}
