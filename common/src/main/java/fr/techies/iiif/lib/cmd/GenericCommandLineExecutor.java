
package fr.techies.iiif.lib.cmd;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.techies.iiif.services.os.OSEnum;

public class GenericCommandLineExecutor {

	private Logger logger = LogManager.getLogger(GenericCommandLineExecutor.class);

	/**
	 * Exécute la commande ImageMagick.
	 * 
	 * @param cmd - commande à lancer
	 */
	// TODO: rethrow exception
	// TODO: manage syserr
	// TODO: rename dirpath
	public String exec(String cmd, String workingDirectory, OSEnum os) {

		ByteArrayOutputStream tmpByteArrayOutputStream = new ByteArrayOutputStream();
		ProcessBuilder builder = new ProcessBuilder();
		byte[] buffer = new byte[1024];

		try {
			switch (os) {
			case Windows:
				builder.command("cmd.exe", "/c", cmd);
				break;

			default:
				builder.command("sh", "-c", cmd);
				break;
			}

			this.logger.info(builder.command().toString());

			builder.directory(new File(workingDirectory));
			Process process = builder.start();

			// Attendre fin du process
			process.waitFor();

			for (int length; (length = process.getInputStream().read(buffer)) != -1;) {
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