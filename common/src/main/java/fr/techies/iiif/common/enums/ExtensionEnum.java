package fr.techies.iiif.common.enums;

/**
 * {@link Enum} representing supported image format.
 * 
 *  @see <a>https://iiif.io/api/image/3.0/#45-format</a>
 */
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
