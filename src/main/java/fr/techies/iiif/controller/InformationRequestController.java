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
import fr.techies.iiif.common.enums.FormatEnum;
import fr.techies.iiif.common.mappers.MediaTypeMapper;
import fr.techies.iiif.controller.validation.InformationRequestParametersValidator;
import fr.techies.iiif.exception.ImageAPIRequestFormatException;
import fr.techies.iiif.exception.ImageNotFoundException;

@Controller
public class InformationRequestController {

	@Autowired
	private InformationRequestParametersValidator informationRequestParametersValidator;
	
	@GetMapping("/{identifier}/info.json")
	public ResponseEntity<?> info(@PathVariable String identifier) {

		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		MediaType mediaType = null;
		byte[] image = null;

		try {
			this.informationRequestParametersValidator.validateParameters(identifier);

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			responseEntity = new ResponseEntity<InfoBean>(new InfoBean(), httpHeaders, HttpStatus.OK);
		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
}
