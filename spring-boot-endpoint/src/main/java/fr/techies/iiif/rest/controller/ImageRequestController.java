package fr.techies.iiif.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidImageRequestException;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;
import fr.techies.iiif.api.imageapi.imagerequest.validator.ImageRequestParametersValidator;
import fr.techies.iiif.common.exception.ImageNotFoundException;
import fr.techies.iiif.rest.mappers.MediaTypeMapper;
import fr.techies.iiif.rest.services.ImageRequestService;

/**
 * <p>
 * As now, image view management (view refer to a sort of page number, for
 * example, a multipage TIFF) is managed in the current controller.
 * </p>
 * <p>
 * It may be best to create another controller to manage view (a controller that
 * extends this one?) that can be maybe deactivated by a spring profile (or
 * maybe at compilation?) for people wanting or not a view number.
 * </p>
 * <p>
 * <b>IMPORTANT</b>: There is no information about view or page in the IIIF
 * image API documentation about view, so assuming that view management is an
 * extra features, we make the arbitrary choice that a <i>normal</i> image
 * request (without view number) retrieve the view number 0.
 * </p>
 * 
 */
@Controller
public class ImageRequestController {

	@Autowired
	private ImageRequestService imageRequestService;

	private ImageRequestParametersValidator imageRequestParametersValidator;

	public ImageRequestController() {
		this.imageRequestParametersValidator = new ImageRequestParametersValidator();
	}

	/**
	 * <p>
	 * Entry point for IIIF requests. Allow view management.
	 * </p>
	 *
	 * <p>
	 * NEVER change string types in primitive type (long, double). Ne pas changer
	 * les types dans leur type définitif (long, double). It is far better to keep
	 * {@link String} types and control manually (without Spring framework
	 * involved).
	 * </p>
	 *
	 * @return {@link ResponseEntity} of bytes (primivite) (raw image). To simplify,
	 *         Spring framework will return an HTTP 200 response and there will be
	 *         the image as byte in the HTTP response body.
	 */
	@GetMapping("/{identifier}/{view}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String identifier, @PathVariable String view,
			@PathVariable String region, @PathVariable String size, @PathVariable String rotation,
			@PathVariable String quality, @PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		ImageRequest imageRequest = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			imageRequest = this.imageRequestParametersValidator.validateParameters(identifier, region, size, rotation,
					quality, format);

			image = this.getResultingImage(imageRequest);

			// Construction du header et don son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(FormatEnum.valueOf(format));
			httpHeaders.setContentType(mediaType);

			// Réponse à retourner
			responseEntity = new ResponseEntity<>(image, httpHeaders, HttpStatus.OK);

		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (InvalidImageRequestException e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	/**
	 * <p>
	 * Entry point for IIIF requests. Disallow view management (view 0 is
	 * retrieved).
	 * </p>
	 *
	 * <p>
	 * NEVER change string types in primitive type (long, double). Ne pas changer
	 * les types dans leur type définitif (long, double). It is far better to keep
	 * {@link String} types and control manually (without Spring framework
	 * involved).
	 * </p>
	 *
	 * @return {@link ResponseEntity} of bytes (primivite) (raw image). To simplify,
	 *         Spring framework will return an HTTP 200 response and there will be
	 *         the image as byte in the HTTP response body.
	 */
	@GetMapping("/{identifier}/{region}/{size}/{rotation}/{quality}.{format}")
	public ResponseEntity<?> imageAPI(@PathVariable String identifier, @PathVariable String region,
			@PathVariable String size, @PathVariable String rotation, @PathVariable String quality,
			@PathVariable String format) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		ImageRequest imageRequest = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			imageRequest = this.imageRequestParametersValidator.validateParameters(identifier, region, size, rotation,
					quality, format);

			image = this.getResultingImage(imageRequest);

			// Construction du header et de son mediaType
			mediaType = MediaTypeMapper.mediaTypeMapper(FormatEnum.valueOf(format));
			httpHeaders.setContentType(mediaType);

			// Réponse à retourner
			responseEntity = new ResponseEntity<>(image, httpHeaders, HttpStatus.OK);

		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		} catch (InvalidImageRequestException e) {
			responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return responseEntity;
	}

	private byte[] getResultingImage(ImageRequest imageRequest) throws ImageNotFoundException {
		return this.imageRequestService.getResultingImage(imageRequest);
	}
}
