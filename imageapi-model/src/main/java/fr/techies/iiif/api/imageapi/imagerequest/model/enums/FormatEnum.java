package fr.techies.iiif.api.imageapi.imagerequest.model.enums;

public enum FormatEnum {

	jpg(".jpg"),
	tif(".tif"),
	png(".png"),
	gif(".gif"),
	jp2(".jp2"),
	pdf(".pdf"),
	webp(".webp");

	private String extension;

	private FormatEnum(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}
