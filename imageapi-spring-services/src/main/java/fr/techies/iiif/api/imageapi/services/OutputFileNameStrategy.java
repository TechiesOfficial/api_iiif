package fr.techies.iiif.api.imageapi.services;

import java.nio.file.Path;

import fr.techies.iiif.lib.utils.enums.ExtensionEnum;

public interface OutputFileNameStrategy {

	public Path getOutputFileName(Path inFileName, ExtensionEnum extensionEnum);
}
