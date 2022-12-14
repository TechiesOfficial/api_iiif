package fr.techies.iiif.api.imageapi.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.util.FileUtil;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.services.command.magick.MagickCmdLineExecutor;
import fr.techies.iiif.api.imageapi.services.image.register.AutoDiscoverImagesFromPathService;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

/**
 * On separe le controller {@link ImageRequestController} de l'implémentation
 * concrète de l'API. L'objectif est à terme de mettre tout ce qui n'est pas web
 * dans une dépendance et un autre jar.
 */
@Service
public class ImageRequestService {

	@Value("${iiif.dir.path}")
	private String dirPath;

	@Autowired
	private MagickCmdLineExecutor magickCmdLineExecutor;

	@Autowired
	private AutoDiscoverImagesFromPathService autoDiscoverImagesFromPathService;

	public byte[] getResultingImage(ImageRequest imageRequest) throws ImageNotFoundException {

		String outFileName = null;
		String inFileName = null;
		byte[] image = null;

		try {
			inFileName = this.autoDiscoverImagesFromPathService.getPath(imageRequest.getIdentifier().getIdentifier())
					.toString();
		} catch (ImageNotFoundException e) {
			throw e;
		}

		outFileName = this.dirPath + "/" + this.autoDiscoverImagesFromPathService
				.getPath(imageRequest.getIdentifier().getIdentifier()).getFileName().toString();

		try {
			image = this.magickCmdLineExecutor.magick(inFileName, outFileName, imageRequest);
		} catch (IOException e) {
			// TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		}

		return image;
	}
}
