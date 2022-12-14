package fr.techies.iiif.api.imageapi.imagerequest.model.enums;

public enum QualityEnum {

	color("color"), gray("gray"), bitonal("bitonal"),
	// Sadly, native and default are java reserved keyword
	native_default("default");

	private final String iiifRequestQuality;

	private QualityEnum(String iiifRequestQuality) {
		this.iiifRequestQuality = iiifRequestQuality;
	}
	
	public static QualityEnum valueOfQuality(String quality) {
		for (QualityEnum qualityEnum : values()) {

			if (qualityEnum.iiifRequestQuality.equals(quality))
				return qualityEnum;
		}
		
		return null;
	}

}
