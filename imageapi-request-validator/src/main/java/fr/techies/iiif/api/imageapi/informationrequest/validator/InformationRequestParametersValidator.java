package fr.techies.iiif.api.imageapi.informationrequest.validator;

import java.util.ArrayList;
import java.util.List;

import fr.techies.iiif.api.imageapi.common.model.Identifier;
import fr.techies.iiif.api.imageapi.informationrequest.model.InformationRequest;

public class InformationRequestParametersValidator {

	public InformationRequest validateParameters(String identifier) {

		List<String> errors = new ArrayList<>();

		return new InformationRequest(new Identifier(identifier));
	}
}
