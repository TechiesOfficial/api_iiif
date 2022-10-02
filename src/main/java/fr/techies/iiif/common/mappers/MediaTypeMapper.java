package fr.techies.iiif.common.mappers;

import org.springframework.http.MediaType;

import fr.techies.iiif.common.enums.ExtensionEnum;

public class MediaTypeMapper {

	public static MediaType mediaTypeMapper(ExtensionEnum format) {
		
		MediaType mediaType = null;
		
		switch(format) {
		
			case jpg :
			case jpeg :
				mediaType = MediaType.IMAGE_JPEG;
				break;
				
			case png :
				mediaType = MediaType.IMAGE_PNG;
				break;
				
			case gif :
				mediaType = MediaType.IMAGE_GIF;
				break;
				
			default:
				mediaType = MediaType.IMAGE_JPEG;
				break;
		}
		
		return mediaType;
	}
}
