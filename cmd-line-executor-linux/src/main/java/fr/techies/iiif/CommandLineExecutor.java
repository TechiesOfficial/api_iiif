
package fr.techies.iiif;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandLineExecutor {

	private Logger logger = LogManager.getLogger(CommandLineExecutor.class);

	/**
	 * Exécute la commande ImageMagick.
	 * 
	 * @param cmd - commande à lancer
	 */
	// TODO: rethrow exception
	// TODO: manage syserr
	// TODO: rename dirpath
	public String exec(String cmd, String workingDirectory) {

		ByteArrayOutputStream tmpByteArrayOutputStream = new ByteArrayOutputStream();
		ProcessBuilder builder = new ProcessBuilder();
		byte[] buffer = new byte[1024];

		try {
			builder.command("sh", "-c", cmd);

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
