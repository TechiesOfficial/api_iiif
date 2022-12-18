package fr.techies.iiif;

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

public class Main {

	public static void main(String[] args) {
		
		ImageRequestService imageRequestService = new ImageRequestService();
		ImageRequest imageRequest = new ImageRequest();
		
		imageRequest.setIdentifier(new Identifier("test"));
		imageRequest.setRegion(new Region(RegionEnum.full));
		imageRequest.setSize(new Size());
		imageRequest.setRotation(new Rotation(false, 0));
		imageRequest.setQuality(new Quality(QualityEnum.native_default));
		imageRequest.setFormat(new Format(FormatEnum.png));
		
		
	}
}
