package fr.techies.iiif.api.imageapi.engine.register;

import java.nio.file.Path;

import fr.techies.iiif.common.exception.ImageNotFoundException;

public interface ImageRegister {

	/**
	 * Récupère une image par rapport à son id.
	 */
	public Path getPath(String identifier) throws ImageNotFoundException;
}
