package fr.techies.iiif.common.mappers;

import org.springframework.http.MediaType;

import fr.techies.iiif.common.enums.FormatEnum;

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

		default:
			mediaType = MediaType.IMAGE_JPEG;
			break;
		}

		return mediaType;
	}
}
