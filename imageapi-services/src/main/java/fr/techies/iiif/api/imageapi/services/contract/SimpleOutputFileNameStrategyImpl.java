package fr.techies.iiif.api.imageapi.services.contract;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.techies.iiif.lib.utils.enums.ExtensionEnum;

public class SimpleOutputFileNameStrategyImpl extends AOutputFileNameStrategy {
	
	public SimpleOutputFileNameStrategyImpl(String outputDirectory) {
		super(outputDirectory);
	}

	@Override
	public Path getOutputFileName(Path originalFileName, ExtensionEnum extensionEnum) {

		return Paths.get(this.outputDirectory + "/" + originalFileName.getFileName() + "." + extensionEnum);
	}
}
