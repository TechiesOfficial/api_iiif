package fr.techies.iiif.api.imageapi.imagerequest.model;

public class RegionPixel {

	private int pixelX;
	
	private int pixelY;
	
	private int pixelW;
	
	private int pixelH;
	
	public RegionPixel(int pixelX, int pixelY, int pixelW, int pixelH) {

		this.pixelX =pixelX;
		this.pixelY=pixelY;
		this.pixelW=pixelW;
		this.pixelH=pixelH;
	}

	public int getPixelX() {
		return pixelX;
	}

	public int getPixelY() {
		return pixelY;
	}

	public int getPixelW() {
		return pixelW;
	}

	public int getPixelH() {
		return pixelH;
	}
}
