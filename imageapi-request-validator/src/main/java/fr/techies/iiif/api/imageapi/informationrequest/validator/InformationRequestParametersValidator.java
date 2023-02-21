package fr.techies.iiif.api.imageapi.informationrequest.validator;

import java.util.ArrayList;
import java.util.List;

import fr.techies.iiif.api.imageapi.imagerequest.model.Identifier;
import fr.techies.iiif.api.imageapi.informationrequest.model.InformationRequest;

public class InformationRequestParametersValidator {

	public InformationRequest validateParameters(String identifier) {

		List<String> errors = new ArrayList<String>();
		
		return new InformationRequest(new Identifier(identifier));
	}
}
