package fr.techies.iiif.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.api.imageapi.informationrequest.validator.InformationRequestParametersValidator;
import fr.techies.iiif.api.imageapi.spring.services.InformationRequestService;
import fr.techies.iiif.api.imageapi.spring.services.InformationResponseBean;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

@Controller
public class InformationRequestController {

	@Autowired
	private InformationRequestService informationRequestService;
	
	private InformationRequestParametersValidator informationRequestParametersValidator;
	
	public InformationRequestController() {
		this.informationRequestParametersValidator = new InformationRequestParametersValidator();
	}
	
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
