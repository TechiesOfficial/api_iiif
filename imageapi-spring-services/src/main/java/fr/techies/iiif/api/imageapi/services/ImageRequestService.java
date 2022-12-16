package fr.techies.iiif.api.imageapi.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.services.command.magick.MagickCmdLineExecutor;
import fr.techies.iiif.api.imageapi.services.image.register.ImageRegister;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

@Service
public class ImageRequestService {

	@Autowired
	private MagickCmdLineExecutor magickCmdLineExecutor;

	@Autowired
	private OutputFileNameStrategy outputFileNameStrategy;
	
	/**
	 * Permet de récupérer l'ensemble des registres d'images dans le classpath.
	 */
	@Autowired
	private List<ImageRegister> imageRegisters;

	public byte[] getResultingImage(ImageRequest imageRequest) throws ImageNotFoundException {

		Path outFileName = null;
		Path inFileName = null;
		byte[] image = null;

		try {
			inFileName = this.getImagePath(imageRequest.getIdentifier().getIdentifier());
		} catch (ImageNotFoundException e) {
			throw e;
		}

		outFileName = this.outputFileNameStrategy.getOutputFileName(inFileName);

		try {
			image = this.magickCmdLineExecutor.magick(inFileName, outFileName, imageRequest);
		} catch (IOException e) {
			// TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		}

		return image;
	}

	private Path getImagePath(String identifier) throws ImageNotFoundException {

		for (ImageRegister imageRegister : imageRegisters) {
			try {
				return imageRegister.getPath(identifier);
			} catch (ImageNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		throw new ImageNotFoundException("Aucune image trouvée dans les repository d'images");
	}
}
