package fr.techies.iiif.web.imageapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.InformationRequestParametersValidator;
import fr.techies.iiif.InformationResponseBean;
import fr.techies.iiif.api.image.services.InformationRequestService;
import fr.techies.iiif.exception.ImageNotFoundException;

@Controller
public class InformationRequestController {

	@Autowired
	private InformationRequestService informationRequestService;
	
	@Autowired
	private InformationRequestParametersValidator informationRequestParametersValidator;
	
	@GetMapping("/{identifier}/info.json")
	public ResponseEntity<?> info(@PathVariable String identifier) {

		InformationResponseBean informationResponseBean = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		
		try {
			this.informationRequestParametersValidator.validateParameters(identifier);

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			informationResponseBean = this.informationRequestService.getInformation(identifier);
			
			responseEntity = new ResponseEntity<InformationResponseBean>(informationResponseBean, httpHeaders, HttpStatus.OK);
		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
}
