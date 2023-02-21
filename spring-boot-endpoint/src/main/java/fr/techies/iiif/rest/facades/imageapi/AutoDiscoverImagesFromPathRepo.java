package fr.techies.iiif.rest.facades.imageapi;

import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;

import fr.techies.iiif.api.imageapi.services.register.AutoDiscoverImagesFromPath;
import fr.techies.iiif.api.imageapi.services.register.ImageRegister;
import fr.techies.iiif.common.exception.ImageNotFoundException;
import fr.techies.iiif.rest.annotations.ImageRepository;

@ImageRepository
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
