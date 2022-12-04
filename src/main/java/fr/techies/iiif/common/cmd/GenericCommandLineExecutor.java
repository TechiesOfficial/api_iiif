package fr.techies.iiif.common.cmd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.services.os.OSDiscoveringService;

@Service
public class GenericCommandLineExecutor {

	private Logger logger = LoggerFactory.getLogger(GenericCommandLineExecutor.class);
	
	@Value("${iiif.dir.path}")
	private String dirPath;
	
	@Autowired
	private OSDiscoveringService osDiscoveringService;
	
	/**
	 * Exécute la commande ImageMagick.
	 * 
	 * @param cmd - commande à lancer
	 */
	//TODO: rethrow exception
	//TODO: manage syserr 
	//TODO: rename dirpath 
	public String exec(String cmd) {

		ByteArrayOutputStream tmpByteArrayOutputStream = new ByteArrayOutputStream();
		ProcessBuilder builder = new ProcessBuilder();
		byte[] buffer = new byte[1024];

		try {
			if (osDiscoveringService.isWindows()) {
				builder.command("cmd.exe", "/c", cmd);
			} else {
				builder.command("sh", "-c", cmd);
			}
			
			this.logger.info(builder.command().toString());

			builder.directory(new File(dirPath));
			Process process = builder.start();

			// Attendre fin du process
			process.waitFor();
			
			for (int length; (length = process.getInputStream().read(buffer)) != -1; ) {
				tmpByteArrayOutputStream.write(buffer, 0, length);
			 }
			
			return tmpByteArrayOutputStream.toString("UTF-8");
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
