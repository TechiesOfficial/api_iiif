package fr.techies.iiif.api.imageapi.imagerequest.model;

public class RegionPCT {

	private double pctX;
	private double pctY;
	private double pctW;
	private double pctH;
	
	public RegionPCT(double pctX,double pctY,double pctW,double pctH) {

		this.pctX = pctX;
		this.pctY=pctY;
		this.pctW=pctW;
		this.pctH=pctH;
	}

	public double getPctX() {
		return pctX;
	}

	public double getPctY() {
		return pctY;
	}

	public double getPctW() {
		return pctW;
	}

	public double getPctH() {
		return pctH;
	}
}
