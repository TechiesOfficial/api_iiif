package fr.techies.iiif.api.imageapi.services.command.magick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.lib.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.magick.IMExecutableUnpacker;
import fr.techies.iiif.services.os.OSEnum;

@Service
public class IdentifyCmdLineExecutor {

	@Value("${iiif.dir.path}")
	private String unpackedTargetPath;
	
	private GenericCommandLineExecutor commandLineExecutor;
	
	@Autowired
	private IdentifyFormatParameterService identifyFormatParameterService;
	
	private IMExecutableUnpacker imExecutableUnpacker;
	
	@PostConstruct
	private void postConstruct() {
		this.imExecutableUnpacker = new IMExecutableUnpacker(OSEnum.Linux, new File(this.unpackedTargetPath));
		this.commandLineExecutor = new GenericCommandLineExecutor();
	}
	
	public IdentifyResultBean identify(Path path) throws IOException {

		StringBuilder sb = new StringBuilder();
		String[] identifyCmdResult = null;
		String output = null;
		
		sb.append(this.imExecutableUnpacker.getIdentifyExecutable());
		sb.append(" ");
		sb.append(this.identifyFormatParameterService.build());
		sb.append(" ");
		sb.append(path.toString());
		

		output = this.commandLineExecutor.exec(sb.toString(), this.unpackedTargetPath, OSEnum.Linux);
		
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
