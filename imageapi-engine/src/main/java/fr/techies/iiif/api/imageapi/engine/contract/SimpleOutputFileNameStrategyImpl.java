package fr.techies.iiif.api.imageapi.engine.contract;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.techies.iiif.common.enums.ExtensionEnum;

public class SimpleOutputFileNameStrategyImpl extends AOutputFileNameStrategy {

	public SimpleOutputFileNameStrategyImpl(String outputDirectory) {
		super(outputDirectory);
	}

	@Override
	public Path getOutputFileName(Path originalFileName, ExtensionEnum originalExtensionEnum) {

		return Paths.get(this.outputDirectory + "/" + originalFileName.getFileName() + "." + originalExtensionEnum);
	}
}
