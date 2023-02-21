package fr.techies.iiif.common.enums;

public enum ExtensionEnum {

	jpg(".jpg"),
	tif(".tif"), 
	png(".png"),
	gif(".gif"), 
	jp2(".jp2"),
	pdf(".pdf"),
	webp(".webp");
	
	private String extension;

	private ExtensionEnum(String extension) {
		this.extension = extension;
	}

	public String getExtension() {
		return extension;
	}
}
