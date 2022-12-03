package fr.techies.iiif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.InfoBean;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.mappers.MediaTypeMapper;
import fr.techies.iiif.exception.ImageAPIRequestFormatException;
import fr.techies.iiif.services.ImageAPIService;

/**
 * On met ici la gestion de vue aussi mais il faudra créér un autre contoller
 * (sous-controller de celui-ci?) activable éventuellement via un profil spring
 * (ou à la compil?) pour les clients voulant un numéro de vue et d'autres non.
 * Appel au point d'entrée sans vue = vue 0?
 */
@Controller
public class ImageAPIController {

	@Autowired
	private ImageAPIService imageAPIService;

	@GetMapping("/{id}/info.json")
	public ResponseEntity<?> info(@PathVariable String id) {

		return new ResponseEntity<InfoBean>(new InfoBean(), HttpStatus.OK);
	}

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
	@GetMapping("/{id}/{view}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String id, @PathVariable String view, @PathVariable String region,
			@PathVariable String size, @PathVariable String rotation, @PathVariable String quality,
			@PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			image = this.getResultingImage(id, view, region, size, rotation, quality, format);

			if (image != null) {
				// Construction du header et don son mediaType
				mediaType = MediaTypeMapper.mediaTypeMapper(ExtensionEnum.valueOf(format));
				httpHeaders.setContentType(mediaType);

				// Réponse à retourner
				responseEntity = new ResponseEntity<byte[]>(image, httpHeaders, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
			}

		} catch (ImageAPIRequestFormatException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
	@GetMapping("/{id}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String id, @PathVariable String region, @PathVariable String size,
			@PathVariable String rotation, @PathVariable String quality, @PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			image = this.getResultingImage(id, null, region, size, rotation, quality, format);

			if (image != null) {
				// Construction du header et don son mediaType
				mediaType = MediaTypeMapper.mediaTypeMapper(ExtensionEnum.valueOf(format));
				httpHeaders.setContentType(mediaType);

				// Réponse à retourner
				responseEntity = new ResponseEntity<byte[]>(image, httpHeaders, HttpStatus.OK);
			} else {
				responseEntity = new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
			}

		} catch (ImageAPIRequestFormatException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	private byte[] getResultingImage(String id, String view, String region, String size, String rotation,
			String quality, String format) throws ImageAPIRequestFormatException {
		return this.imageAPIService.getResultingImage(id, view, region, size, rotation, quality, format);
	}
}
