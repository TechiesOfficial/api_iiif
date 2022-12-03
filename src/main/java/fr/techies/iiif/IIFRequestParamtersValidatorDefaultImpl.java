package fr.techies.iiif;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.techies.iiif.controller.validation.IIIFRequestParametersValidator;

@Service
public class IIFRequestParamtersValidatorDefaultImpl extends IIIFRequestParametersValidator {

	@Override
	protected List<String> validateID() {

		return new ArrayList<String>();
	}
}
