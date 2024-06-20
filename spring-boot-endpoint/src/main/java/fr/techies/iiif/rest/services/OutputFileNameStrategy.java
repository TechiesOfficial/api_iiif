package fr.techies.iiif.rest.services;

import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Component;

import fr.techies.iiif.api.imageapi.engine.contract.AOutputFileNameStrategy;
import jakarta.annotation.PostConstruct;

@Component
@ConditionalOnProperty(value = "imageapi.outputfilename.strategy", matchIfMissing = true)
public class OutputFileNameStrategy {

	@Value("${imageapi.outputTargetPath}")
	private String outputTargetPath;

	@Value("${imageapi.outputfilename.strategy}")
	private String strategy;

	private AOutputFileNameStrategy outputFileNameStrategy;

	@PostConstruct
	private void postConstructValidation() {

		if (this.outputTargetPath == null) {
			throw new InvalidConfigurationPropertyValueException("dirpath", outputTargetPath,
					"The use of SImpleOutputFileNameStrategy need the key dir.path in configuration");
		}

		try {
			Class<?> strategyClass = Class.forName(this.strategy);

			this.outputFileNameStrategy = (AOutputFileNameStrategy) strategyClass.getConstructor(String.class).newInstance(this.outputTargetPath);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public AOutputFileNameStrategy getOutputFileNameStrategy() {
		return outputFileNameStrategy;
	}
}
