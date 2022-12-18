package fr.techies.iiif.api.imageapi.services.contract;

import java.nio.file.Path;
import java.nio.file.Paths;

import fr.techies.iiif.lib.utils.enums.ExtensionEnum;

public class SImpleOutputFileNameStrategyImpl extends AOutputFileNameStrategy {
	
	public SImpleOutputFileNameStrategyImpl(String outputDirectory) {
		super(outputDirectory);
	}

	@Override
	public Path getOutputFileName(Path originalFileName, ExtensionEnum extensionEnum) {

		return Paths.get(this.outputDirectory + "/" + originalFileName.getFileName() + "." + extensionEnum);
	}
}
