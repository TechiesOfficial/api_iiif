package fr.techies.iiif.api.image.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.InformationResponseBean;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.services.command.identify.IdentifyCmdLineExecutor;
import fr.techies.iiif.services.command.identify.IdentifyResultBean;
import fr.techies.iiif.services.image.register.AutoDiscoverImagesFromPathService;

/**
 * On separe le controller {@link ImageRequestController} de l'implémentation
 * concrète de l'API. L'objectif est à terme de mettre tout ce qui n'est pas web
 * dans une dépendance et un autre jar.
 */
@Service
public class InformationRequestService {

	@Autowired
	private IdentifyCmdLineExecutor identifyCmdLineExecutor;

	@Autowired
	private AutoDiscoverImagesFromPathService autoDiscoverImagesFromPathService;

	public InformationResponseBean getInformation(String identifier) throws ImageNotFoundException {

		InformationResponseBean informationResponseBean = new InformationResponseBean();
		IdentifyResultBean image = null;
		List<String> errors = null;
		String outFileName = null;
		String inFileName = null;

		try {
			inFileName = this.autoDiscoverImagesFromPathService.getPath(identifier).toString();

			image = this.identifyCmdLineExecutor.identify(inFileName);

			informationResponseBean.setWidth(image.getWitdth());
			informationResponseBean.setHeight(image.getHeight());
			
		} catch (ImageNotFoundException e) {
			throw e;
		}
		catch (IOException e) {
			// TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		}

		return informationResponseBean;
	}
}
