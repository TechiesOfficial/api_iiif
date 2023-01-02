package fr.techies.iiif.rest.facades.magick;


import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.magick.IMExecutableUnpacker;
import fr.techies.iiif.services.os.OSEnum;

@Service
public class ImageMagickExecutableFinder {

	private Logger logger = LogManager.getLogger(ImageMagickExecutableFinder.class);

	@Value("imageMagickExecutableFinder.path")
	private String imExecutableTargetPath;

	private IMExecutableUnpacker imExecutableUnpacker;
	
	public ImageMagickExecutableFinder() {

		IMExecutableUnpacker imExecutableUnpacker = new IMExecutableUnpacker(OSEnum.Windows, new File(this.imExecutableTargetPath));

		this.imExecutableUnpacker = imExecutableUnpacker;
	}
	
	public File getMagickExecutable() throws IOException {

		return this.imExecutableUnpacker.getMagickExecutable();
	}
	
	public File getIdentifyExecutable() throws IOException {

		return this.imExecutableUnpacker.getIdentifyExecutable();
	}
}
