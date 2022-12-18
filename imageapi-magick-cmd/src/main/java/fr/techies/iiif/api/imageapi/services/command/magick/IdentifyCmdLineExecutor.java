package fr.techies.iiif.api.imageapi.services.command.magick;

import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.lib.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.magick.IMExecutableUnpacker;

@Service
public class IdentifyCmdLineExecutor {

	@Autowired
	private IMExecutableUnpacker executableFinder;

	@Autowired
	private GenericCommandLineExecutor commandLineExecutor;
	
	@Autowired
	private IdentifyFormatParameterService identifyFormatParameterService;
	
	public IdentifyResultBean identify(Path path) throws IOException {

		StringBuilder sb = new StringBuilder();
		String[] identifyCmdResult = null;
		String output = null;
		
		sb.append(this.executableFinder.getIdentifyExecutable());
		sb.append(" ");
		sb.append(this.identifyFormatParameterService.build());
		sb.append(" ");
		sb.append(path.toString());
		

		output = this.commandLineExecutor.exec(sb.toString());
		
		identifyCmdResult = output.split(",");
		
		return this.mapToBean(identifyCmdResult);
	}

	private IdentifyResultBean mapToBean(String[] identifyCmdResult) {
		IdentifyResultBean identifyResultBean = new IdentifyResultBean();
		
		identifyResultBean.setFilesize(identifyCmdResult[0]);
		identifyResultBean.setFilenameExtension(identifyCmdResult[1]);
		identifyResultBean.setFileName(identifyCmdResult[2]);
		identifyResultBean.setWitdth(identifyCmdResult[3]);
		identifyResultBean.setHeight(identifyCmdResult[4]);
		identifyResultBean.setxRes(identifyCmdResult[5]);
		identifyResultBean.setyRes(identifyCmdResult[6]);
		
		return identifyResultBean;
	}
}
