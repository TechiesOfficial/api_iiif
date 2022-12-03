package fr.techies.iiif.model;

import fr.techies.iiif.common.enums.ExtensionEnum;

public class RequestsIIIFBean {

	private String id;

	private String region;

	private String size;

	private boolean mirroring;

	private String rotation;

	private String quality;

	private ExtensionEnum format;

	public RequestsIIIFBean() {
	}

	public RequestsIIIFBean(String id, String region, String size, String rotation, String quality,
			ExtensionEnum format) {
		this.id = id;
		this.region = region;
		this.size = size;
		this.rotation = rotation;
		this.quality = quality;
		this.format = format;
		if(rotation.contains("!"))
			this.mirroring=true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getRotation() {
		return rotation;
	}

	public void setRotation(String rotation) {
		this.rotation = rotation;
	}

	public String getQuality() {
		return quality;
	}

	public void setQuality(String quality) {
		this.quality = quality;
	}

	public ExtensionEnum getFormat() {
		return format;
	}

	public void setFormat(ExtensionEnum format) {
		this.format = format;
	}

	public boolean isMirroring() {
		return mirroring;
	}

	public void setMirroring(boolean mirroring) {
		this.mirroring = mirroring;
	}

}
