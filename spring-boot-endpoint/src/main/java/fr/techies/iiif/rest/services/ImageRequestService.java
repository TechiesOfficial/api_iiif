package fr.techies.iiif.rest.services;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.services.ImageRequestProcessor;
import fr.techies.iiif.api.imageapi.services.image.register.ImageRegister;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

@Service
public class ImageRequestService {
	
	@Value("${imageapi.unpackedTargetPath}")
	private String unpackedTargetPath;

	@Autowired
	private OutputFileNameStrategy outputFileNameStrategy;

	@Autowired
	private List<ImageRegister> imageRegisters;
	
	private ImageRequestProcessor imageRequestProcessor;

	@PostConstruct
	private void postConstruct() {
		this.imageRequestProcessor = new ImageRequestProcessor(this.outputFileNameStrategy.getOutputFileNameStrategy(), imageRegisters, unpackedTargetPath);
	}
	
	public byte[] getResultingImage(ImageRequest imageRequest) throws ImageNotFoundException {

		return this.imageRequestProcessor.getResultingImage(imageRequest);
	}
}