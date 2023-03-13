package fr.techies.iiif.api.imageapi.engine;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import fr.techies.iiif.api.imageapi.engine.register.ImageRegister;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.informationrequest.model.InformationRequest;
import fr.techies.iiif.api.imageapi.services.command.magick.IdentifyCmdLineExecutor;
import fr.techies.iiif.api.imageapi.services.command.magick.IdentifyResultBean;
import fr.techies.iiif.common.exception.ImageNotFoundException;

public class InformationRequestProcessor {

	private IdentifyCmdLineExecutor identifyCmdLineExecutor;

	private List<ImageRegister> imageRegisters;

	public InformationRequestProcessor(List<ImageRegister> imageRegisters, String unpackedTargetPath) {
		this.identifyCmdLineExecutor = new IdentifyCmdLineExecutor(unpackedTargetPath);
		this.imageRegisters = imageRegisters;
	}

	public IdentifyResultBean identify(InformationRequest informationRequest) throws ImageNotFoundException {

		IdentifyResultBean identifyResultBean = null;
		Path inFileName = null;

		try {
			inFileName = this.getImagePath(informationRequest.getIdentifier().getIdentifier());
			identifyResultBean = this.identifyCmdLineExecutor.identify(inFileName);
		} catch (IOException e) {
			// TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		} catch (ImageNotFoundException e) {
			throw e;
		}

		return identifyResultBean;
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
