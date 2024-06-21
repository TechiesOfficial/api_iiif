package fr.techies.iiif.api.imageapi.engine.register;

import java.nio.file.Path;

import fr.techies.iiif.api.imageapi.common.model.Identifier;
import fr.techies.iiif.common.exception.ImageNotFoundException;

public interface ImageRegister {

	/**
	 * Retrieve an image based on its {@link Identifier}.
	 * 
	 * @param identifier Image identifier
	 * 
	 * @return the {@link Path} to the image.
	 * 
	 * @throws ImageNotFoundException 
	 */
	public Path getPath(String identifier) throws ImageNotFoundException;
}
