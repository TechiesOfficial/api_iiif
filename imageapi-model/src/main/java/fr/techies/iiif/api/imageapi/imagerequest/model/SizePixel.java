package fr.techies.iiif.api.imageapi.imagerequest.model;

public class SizePixel {

	private int pixelW;

	private int pixelH;

	public SizePixel(int pixelW, int pixelH) {

		this.pixelW=pixelW;
		this.pixelH=pixelH;
	}

	public int getPixelW() {
		return pixelW;
	}

	public int getPixelH() {
		return pixelH;
	}
}
