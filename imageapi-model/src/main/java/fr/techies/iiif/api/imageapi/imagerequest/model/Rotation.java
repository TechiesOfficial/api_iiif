package fr.techies.iiif.api.imageapi.imagerequest.model;

public class Rotation {

	private boolean mirroring;

	private int degree;

	public Rotation(boolean mirroring, int degree) {

		this.mirroring = mirroring;
		this.degree = degree;
	}

	public boolean isMirroring() {
		return mirroring;
	}

	public int getDegree() {
		return degree;
	}
}
