package fr.techies.iiif.api.imageapi.services.image.register;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;

import fr.techies.iiif.api.imageapi.services.image.register.annotation.ImageRepository;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

@ImageRepository
public class AutoDiscoverImagesFromPathRepo implements ImageRegister{

	@Value("${autoDiscoverImagesFromPathRepo.path}")
	private Path path;
	
	private AutoDiscoverImagesFromPath autoDiscoverImagesFromPath;
	
	public AutoDiscoverImagesFromPathRepo() {
	
		this.autoDiscoverImagesFromPath = new AutoDiscoverImagesFromPath(this.path);
	}

	@Override
	public Path getPath(String identifier) throws ImageNotFoundException {
		return this.autoDiscoverImagesFromPath.getPath(identifier);
	}
}
