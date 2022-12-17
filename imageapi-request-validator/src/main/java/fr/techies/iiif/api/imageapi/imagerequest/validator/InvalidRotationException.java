package fr.techies.iiif.api.imageapi.imagerequest.validator;

import fr.techies.iiif.imageapi.exception.ImageRequestFormatException;

public class InvalidRotationException extends InvalidImageRequestException {

	public InvalidRotationException(String message) {
		super(message);
	}
}
