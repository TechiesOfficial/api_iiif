package fr.techies.iiif.api.imageapi.services.image.register;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

public class AutoDiscoverImagesFromPathRepo implements ImageRegister{

	@Value("${autoDiscoverImagesFromPathRepo.path}")
	private String path;
	
	private AutoDiscoverImagesFromPath autoDiscoverImagesFromPath;
	
	@PostConstruct
	private void postConstruct() {
		this.autoDiscoverImagesFromPath = new AutoDiscoverImagesFromPath(Paths.get(path));
	}
	
	@Override
	public Path getPath(String identifier) throws ImageNotFoundException {
		return this.autoDiscoverImagesFromPath.getPath(identifier);
	}
}
