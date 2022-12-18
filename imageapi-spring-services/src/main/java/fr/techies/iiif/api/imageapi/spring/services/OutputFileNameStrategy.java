package fr.techies.iiif.api.imageapi.spring.services;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.source.InvalidConfigurationPropertyValueException;
import org.springframework.stereotype.Component;

import fr.techies.iiif.api.imageapi.services.contract.AOutputFileNameStrategy;
import fr.techies.iiif.api.imageapi.services.contract.SImpleOutputFileNameStrategyImpl;
import fr.techies.iiif.lib.utils.enums.ExtensionEnum;

@Component
@ConditionalOnProperty(value = "imageapi.outputfilename.strategy", matchIfMissing = true)
public class OutputFileNameStrategy {

	@Value("${iiif.dir.path}")
	private String outputTargetPath;

	@Value("${imageapi.outputfilename.strategy}")
	private String strategy;

	private AOutputFileNameStrategy outputFileNameStrategy;

	@PostConstruct
	private void postConstructValidation() {

		if (this.outputTargetPath == null)
			throw new InvalidConfigurationPropertyValueException("dirpath", outputTargetPath,
					"The use of SImpleOutputFileNameStrategy need the key dir.path in configuration");

		try {
			Class<?> strategyClass = Class.forName(this.strategy);

			this.outputFileNameStrategy = (AOutputFileNameStrategy) strategyClass.getConstructor(String.class).newInstance(this.outputTargetPath);;

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