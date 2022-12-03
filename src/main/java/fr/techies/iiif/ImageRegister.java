package fr.techies.iiif;

import java.nio.file.Path;

import fr.techies.iiif.exception.ImageNotFoundException;

public interface ImageRegister {

	/**
	 * Récupère une image par rapport à son id.
	 */
	public Path getPath(String imageId) throws ImageNotFoundException;
}
