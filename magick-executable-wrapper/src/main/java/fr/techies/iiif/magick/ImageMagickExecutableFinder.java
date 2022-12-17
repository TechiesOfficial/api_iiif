package fr.techies.iiif.magick;

import java.io.IOException;
import java.util.logging.LogManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ImageMagickExecutableFinder {

	private Logger logger = LogManager.getLogger(ImageMagickExecutableFinder.class);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	public String getMagickExecutable() throws IOException {

		Resource resource = resourceLoader.getResource("classpath:magick/win/magick.exe");
		
		return this.getExePath(resource);
	}
	
	public String getIdentifyExecutable() throws IOException {

		Resource resource = resourceLoader.getResource("classpath:magick/win/identify.exe");

		return this.getExePath(resource);
	}
	
	private String getExePath(Resource resource) throws IOException {
		
		String exePath = null;
		
		try {
			if (!resource.exists()) {
				logger.error("Impossible de trouver le chemin vers l'exécutable " + resource.getDescription());
			} else {
				exePath = resource.getFile().getAbsolutePath();
				logger.info("Le path vers l'exécutable " + resource.getDescription() + " est : " + exePath);
			}
		} catch (IOException e) {
			// Une erreur non prévue. On rethrow pour tout arrêter
			throw e;
		}
		
		return exePath;
	}
}
