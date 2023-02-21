package fr.techies.iiif.api.imageapi.services.contract;

import java.nio.file.Path;

import fr.techies.iiif.common.enums.ExtensionEnum;

public abstract class AOutputFileNameStrategy {

	protected String outputDirectory;

	public AOutputFileNameStrategy(String outputDirectory) {

		this.outputDirectory = outputDirectory;
	}

	public abstract Path getOutputFileName(Path inFileName, ExtensionEnum extensionEnum);
}
