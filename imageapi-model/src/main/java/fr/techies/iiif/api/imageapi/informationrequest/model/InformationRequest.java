package fr.techies.iiif.api.imageapi.informationrequest.model;

import fr.techies.iiif.api.imageapi.common.model.Identifier;

public class InformationRequest {

	private Identifier identifier;

	public InformationRequest(Identifier identifier) {

		this.identifier = identifier;
	}

	public Identifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(Identifier identifier) {
		this.identifier = identifier;
	}
}
