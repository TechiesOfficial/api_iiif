package fr.techies.iiif.rest.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.engine.InformationRequestProcessor;
import fr.techies.iiif.api.imageapi.engine.register.ImageRegister;
import fr.techies.iiif.api.imageapi.informationrequest.model.InformationRequest;
import fr.techies.iiif.api.imageapi.services.command.magick.IdentifyResultBean;
import fr.techies.iiif.common.exception.ImageNotFoundException;
import fr.techies.iiif.rest.controller.ImageRequestController;

/**
 * On separe le controller {@link ImageRequestController} de l'implémentation
 * concrète de l'API. L'objectif est à terme de mettre tout ce qui n'est pas web
 * dans une dépendance et un autre jar.
 */
@Service
public class InformationRequestService {

	@Value("${imageapi.imagemagick.unpackedTargetPath}")
	private String unpackedTargetPath;

	@Autowired
	private List<ImageRegister> imageRegisters;

	private InformationRequestProcessor informationRequestProcessor;

	@PostConstruct
	public void postConstruct() {
		this.informationRequestProcessor = new InformationRequestProcessor(imageRegisters, unpackedTargetPath);
	}

	public InformationResponseBean getInformation(InformationRequest informationRequest) throws ImageNotFoundException {

		InformationResponseBean informationResponseBean = new InformationResponseBean();
		IdentifyResultBean identifyResultBean = null;

		identifyResultBean = this.informationRequestProcessor.identify(informationRequest);

		informationResponseBean.setWidth(identifyResultBean.getWidth());
		informationResponseBean.setHeight(identifyResultBean.getHeight());

		return informationResponseBean;
	}
}
