package fr.techies.iiif.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fr.techies.iiif.api.imageapi.informationrequest.model.InformationRequest;
import fr.techies.iiif.api.imageapi.informationrequest.validator.InformationRequestParametersValidator;
import fr.techies.iiif.common.exception.ImageNotFoundException;
import fr.techies.iiif.rest.services.InformationRequestService;
import fr.techies.iiif.rest.services.InformationResponseBean;

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
		InformationRequest informationRequest = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		ResponseEntity<?> responseEntity = null;
		
		try {
			informationRequest = this.informationRequestParametersValidator.validateParameters(identifier);

			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			informationResponseBean = this.informationRequestService.getInformation(informationRequest);
			
			responseEntity = new ResponseEntity<InformationResponseBean>(informationResponseBean, httpHeaders, HttpStatus.OK);
		} catch (ImageNotFoundException e) {
			responseEntity = new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		return responseEntity;
	}
}
