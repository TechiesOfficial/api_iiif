package fr.techies.iiif.api.imageapi.services.command.magick;

public class IdentifyResultBean {
	private String filesize;
	private String filenameExtension;
	private String fileName;
	private String witdth;
	private String height;
	private String xRes;
	private String yRes;

	public String getFilesize() {
		return filesize;
	}

	public void setFilesize(String filesize) {
		this.filesize = filesize;
	}

	public String getFilenameExtension() {
		return filenameExtension;
	}

	public void setFilenameExtension(String filenameExtension) {
		this.filenameExtension = filenameExtension;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getWitdth() {
		return witdth;
	}

	public void setWitdth(String witdth) {
		this.witdth = witdth;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getxRes() {
		return xRes;
	}

	public void setxRes(String xRes) {
		this.xRes = xRes;
	}

	public String getyRes() {
		return yRes;
	}

	public void setyRes(String yRes) {
		this.yRes = yRes;
	}
}
