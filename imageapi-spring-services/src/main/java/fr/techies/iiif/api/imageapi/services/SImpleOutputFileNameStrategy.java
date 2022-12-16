package fr.techies.iiif.api.imageapi.services;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "imageapi.outputfilename.strategy", havingValue = "SImpleOutputFileNameStrategy", matchIfMissing = true)
public class SImpleOutputFileNameStrategy implements OutputFileNameStrategy {

	@Value("${iiif.dir.path}")
	private String dirPath;

	@PostConstruct
	private void postConstructValidation() {

		if (this.dirPath == null)
			throw new InvalidConfigurationPropertyValueException("dirpath", dirPath,
					"The use of SImpleOutputFileNameStrategy need the key dir.path in configuration");
	}

	@Override
	public Path getOutputFileName(Path originalFileName) {

		return Paths.get(this.dirPath + "/" + originalFileName.getFileName());
	}
}
