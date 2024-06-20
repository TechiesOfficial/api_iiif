package fr.techies.iiif.rest.facades.magick;


import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.magick.IMExecutableUnpacker;
import jakarta.annotation.PostConstruct;

/*
 * TODO : voir pertinence !
 */
@Service
public class ImageMagickExecutableUnpackerService {

	private Logger logger = LogManager.getLogger(ImageMagickExecutableUnpackerService.class);

	@Value("${imageapi.imagemagick.unpackedTargetPath}")
	private String imExecutableTargetPath;

	private IMExecutableUnpacker imExecutableUnpacker;

	@PostConstruct
	public void init() {

		IMExecutableUnpacker imExecutableUnpacker = new IMExecutableUnpacker(new File(this.imExecutableTargetPath));

		this.imExecutableUnpacker = imExecutableUnpacker;
	}

	public File getMagickExecutable() throws IOException {

		return this.imExecutableUnpacker.getMagickExecutable();
	}

}
