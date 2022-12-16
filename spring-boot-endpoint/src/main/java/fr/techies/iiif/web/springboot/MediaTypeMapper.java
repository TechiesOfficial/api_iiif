package fr.techies.iiif.web.springboot;

import org.springframework.http.MediaType;
import org.springframework.util.MimeType;

import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;

public class MediaTypeMapper {

	public static MediaType mediaTypeMapper(FormatEnum format) {

		MediaType mediaType = null;

		switch (format) {

		case jpg:
			mediaType = MediaType.IMAGE_JPEG;
			break;

		case png:
			mediaType = MediaType.IMAGE_PNG;
			break;

		case gif:
			mediaType = MediaType.IMAGE_GIF;
			break;

		case pdf:
			mediaType = MediaType.APPLICATION_PDF;
			break;
		case tif:
		case jp2:
			mediaType = MediaType.APPLICATION_OCTET_STREAM;
			break;
			
		case webp:
			mediaType = new MediaType(new MimeType("image" , "webp"));
			break;

		default:
			mediaType = MediaType.ALL;
			break;
		}

		return mediaType;
	}
}
