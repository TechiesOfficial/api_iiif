package fr.techies.iiif;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class IIFRequestParamtersValidatorDefaultImpl extends IIIFRequestParametersValidator{

	@Override
	protected List<String> validateIDandPage() {
		
		return null;
	}
}
