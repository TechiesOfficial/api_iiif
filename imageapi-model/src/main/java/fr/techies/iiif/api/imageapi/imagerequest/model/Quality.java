package fr.techies.iiif.api.imageapi.imagerequest.model;

import fr.techies.iiif.api.imageapi.imagerequest.model.enums.QualityEnum;

public class Quality {

	private QualityEnum qualityEnum;
	
	public Quality(QualityEnum qualityEnum) {
		this.qualityEnum =qualityEnum;
	}

	public QualityEnum getQualityEnum() {
		return qualityEnum;
	}
}
