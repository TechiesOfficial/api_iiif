package fr.techies.iiif.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.controller.ImageAPIController;
import fr.techies.iiif.controller.validation.IIIFRequestParametersValidator;
import fr.techies.iiif.exception.ImageAPIRequestFormatException;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.model.RequestsIIIFBean;
import fr.techies.iiif.services.command.magick.MagickCmdLineExecutor;
import fr.techies.iiif.services.image.register.AutoDiscoverImagesFromPathService;

/**
 * On separe le controller {@link ImageAPIController} de l'implémentation concrète de l'API.
 * L'objectif est à terme de mettre tout ce qui n'est pas web dans une dépendance et un autre jar.
 */
@Service
public class ImageAPIService {

	@Value("${iiif.dir.path}")
	private String dirPath;
	
	@Autowired
	private IIIFRequestParametersValidator iiifRequestParametersValidator;

	@Autowired
	private MagickCmdLineExecutor magickCmdLineExecutor;
	
	@Autowired
	private AutoDiscoverImagesFromPathService autoDiscoverImagesFromPathService;
	
	public byte[] getResultingImage(String id, String view, String region, String size, String rotation, String quality,
			String format) throws ImageAPIRequestFormatException, ImageNotFoundException {
		
		List<String> errors = null;
		String outFileName = null;	
		String inFileName = null;
		byte[] image = null;

		errors = this.iiifRequestParametersValidator.validateParameters(id, region, size, rotation, quality, format);
		if (!errors.isEmpty()) {

			StringBuilder sb = new StringBuilder("<html><body>");
			for (String error : errors) {

				sb.append("<ul>" + error + "</ul>");
			}

			throw new ImageAPIRequestFormatException(sb.toString());
		}
		
		try {
			inFileName = this.autoDiscoverImagesFromPathService.getPath(id).toString();
		} catch (ImageNotFoundException e) {
			throw e;
		}
		
		outFileName = this.dirPath + "/" + this.autoDiscoverImagesFromPathService.getPath(id).getFileName() + "." + format;
		
		try {
			image = this.magickCmdLineExecutor.magick(inFileName, outFileName, region, size, rotation, quality,
					format);
		} catch (IOException e) {
			//TODO: améliorer même si cela ne devrait jamais se produire
			e.printStackTrace();
		}
		
		return image;
	}
}
