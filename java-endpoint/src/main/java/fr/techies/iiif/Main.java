package fr.techies.iiif;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.techies.iiif.api.imageapi.imagerequest.model.Format;
import fr.techies.iiif.api.imageapi.imagerequest.model.Identifier;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.Quality;
import fr.techies.iiif.api.imageapi.imagerequest.model.Region;
import fr.techies.iiif.api.imageapi.imagerequest.model.Rotation;
import fr.techies.iiif.api.imageapi.imagerequest.model.Size;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.QualityEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.RegionEnum;
import fr.techies.iiif.api.imageapi.services.ImageRequestProcessor;
import fr.techies.iiif.api.imageapi.services.contract.SimpleOutputFileNameStrategyImpl;
import fr.techies.iiif.api.imageapi.services.image.register.AutoDiscoverImagesFromPath;
import fr.techies.iiif.api.imageapi.services.image.register.ImageRegister;
import fr.techies.iiif.imageapi.exception.ImageNotFoundException;

public class Main {

	public static void main(String[] args) {

		ImageRequest imageRequest = new ImageRequest();

		imageRequest.setIdentifier(new Identifier("file_example_TIFF_1MB.tiff"));
		imageRequest.setRegion(new Region(RegionEnum.full));
//		imageRequest.setSize(new Size());
		imageRequest.setRotation(new Rotation(false, 0));
		imageRequest.setQuality(new Quality(QualityEnum.native_default));
		imageRequest.setFormat(new Format(FormatEnum.png));

		List<ImageRegister> imageRegisters = new ArrayList<ImageRegister>();
		imageRegisters.add(new AutoDiscoverImagesFromPath(Paths.get("C:\\Users\\BNFTT\\Desktop\\techies\\images")));
		
		ImageRequestProcessor imageRequestProcessor = new ImageRequestProcessor(
				new SimpleOutputFileNameStrategyImpl("C:\\Users\\BNFTT\\Desktop\\techies\\images"), imageRegisters, "/home/romain/Images/exe");

		try {
			byte[] image = imageRequestProcessor.getResultingImage(imageRequest);
			
			
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
