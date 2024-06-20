package fr.techies.iiif.api.imageapi.imagerequest.model;

import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;

public class Format {

	private FormatEnum format;

	public Format(FormatEnum format) {

		this.format = format;
	}

	public FormatEnum getFormat() {
		return format;
	}
}
