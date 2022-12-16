package fr.techies.iiif.api.imageapi.services;

import java.nio.file.Path;

public interface OutputFileNameStrategy {

	public Path getOutputFileName(Path inFileName);
}
