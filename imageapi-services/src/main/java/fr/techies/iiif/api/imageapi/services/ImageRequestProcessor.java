package fr.techies.iiif.api.imageapi.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.services.command.magick.MagickCmdLineExecutor;
import fr.techies.iiif.api.imageapi.services.contract.AOutputFileNameStrategy;
import fr.techies.iiif.api.imageapi.services.image.register.ImageRegister;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;
import fr.techies.iiif.lib.utils.enums.ExtensionEnum;
import fr.techies.iiif.services.os.OSEnum;

public class ImageRequestProcessor {
	
	private MagickCmdLineExecutor magickCmdLineExecutor;

	private AOutputFileNameStrategy outputFileNameStrategy;

	private List<ImageRegister> imageRegisters;

	public ImageRequestProcessor(AOutputFileNameStrategy outputFileNameStrategy, List<ImageRegister> imageRegisters, String unpackedTargetPath,OSEnum osEnum) {
		this.magickCmdLineExecutor = new MagickCmdLineExecutor(unpackedTargetPath, osEnum);
		this.outputFileNameStrategy = outputFileNameStrategy;
		this.imageRegisters = imageRegisters;
	}

	public byte[] getResultingImage(ImageRequest imageRequest) throws ImageNotFoundException {

		Path outFileName = null;
		Path inFileName = null;
		byte[] image = null;

		try {
			inFileName = this.getImagePath(imageRequest.getIdentifier().getIdentifier());

			outFileName = this.outputFileNameStrategy.getOutputFileName(inFileName,
				ExtensionEnum.valueOf(imageRequest.getFormat().getFormat().toString()));

			image = this.magickCmdLineExecutor.magick(inFileName, outFileName, imageRequest);
		} catch (IOException e) {
			// TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		} catch (ImageNotFoundException e) {
			throw e;
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
