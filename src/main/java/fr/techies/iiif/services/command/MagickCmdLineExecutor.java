package fr.techies.iiif.services.command;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

public class MagickCmdLineExecutor {

	@Autowired
	private ImageMagickExecutableFinder executableFinder;

	@Autowired
	private GenericCommandLineExecutor commandLineExecutor;
	
	public void magick(String inFileName, String outFileName) throws IOException {

		StringBuilder sb = new StringBuilder();
		String output = null;
		
		sb.append(this.executableFinder.getMagickExecutable());
		sb.append(" ");
		sb.append(inFileName);
		sb.append(" ");
		sb.append(outFileName);

		this.commandLineExecutor.exec(sb.toString());
		
	}
}
