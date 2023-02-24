package fr.techies.iiif.api.imageapi.services.command.magick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import fr.techies.iiif.CommandLineExecutor;
import fr.techies.iiif.magick.IMExecutableUnpacker;

public class IdentifyCmdLineExecutor {

	private String unpackedTargetPath;
	
	private CommandLineExecutor commandLineExecutor;
	
	private IdentifyFormatParameterService identifyFormatParameterService;
	
	private IMExecutableUnpacker imExecutableUnpacker;
	
	public IdentifyCmdLineExecutor(String unpackedTargetPath) {
		this.unpackedTargetPath = unpackedTargetPath;
		this.imExecutableUnpacker = new IMExecutableUnpacker(new File(this.unpackedTargetPath));
		this.commandLineExecutor = new CommandLineExecutor();
		this.identifyFormatParameterService = new IdentifyFormatParameterService();
	}
	
	public IdentifyResultBean identify(Path path) throws IOException {

		StringBuilder sb = new StringBuilder();
		String[] identifyCmdResult = null;
		String output = null;
		
		sb.append(this.imExecutableUnpacker.getMagickExecutable());
		sb.append(" ");
		sb.append("identify");
		sb.append(" ");
		sb.append(this.identifyFormatParameterService.build());
		sb.append(" ");
		sb.append(path.toString());
		
		output = this.commandLineExecutor.exec(sb.toString(), this.unpackedTargetPath);
		
		identifyCmdResult = output.split(",");
		
		return this.mapToBean(identifyCmdResult);
	}

	private IdentifyResultBean mapToBean(String[] identifyCmdResult) {
		IdentifyResultBean identifyResultBean = new IdentifyResultBean();
		
		identifyResultBean.setFilesize(identifyCmdResult[0]);
		identifyResultBean.setFilenameExtension(identifyCmdResult[1]);
		identifyResultBean.setFileName(identifyCmdResult[2]);
		identifyResultBean.setWidth(identifyCmdResult[3]);
		identifyResultBean.setHeight(identifyCmdResult[4]);
		identifyResultBean.setxRes(identifyCmdResult[5]);
		identifyResultBean.setyRes(identifyCmdResult[6]);
		
		return identifyResultBean;
	}
}
