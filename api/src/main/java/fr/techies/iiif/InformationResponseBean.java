package fr.techies.iiif;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"@context", "id", "type", "protocol", "profile", "width", "height", "maxWidth", "maxHeight", "maxArea"})
@JsonInclude(Include.NON_NULL)
public class InformationResponseBean {

	@JsonProperty("@context")
	private String context = "http://iiif.io/api/image/3/context.json";

	private String id;

	private String type = "ImageService3";

	private String protocol = "http://iiif.io/api/image";

	private String profile = "level2";
	
	private String width;

	private String height;

	private String maxWidth;

	private String maxHeight;

	private String maxArea;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(String maxWidth) {
		this.maxWidth = maxWidth;
	}

	public String getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(String maxHeight) {
		this.maxHeight = maxHeight;
	}

	public String getMaxArea() {
		return maxArea;
	}

	public void setMaxArea(String maxArea) {
		this.maxArea = maxArea;
	}
}
