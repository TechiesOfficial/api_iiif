package fr.techies.iiif.services.os;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class OSDiscoveringService {

	private Logger logger = LogManager.getLogger(OSDiscoveringService.class);

	private boolean isWindows = true;

	public boolean isWindows() {

		String osName = System.getProperty("os.name").toLowerCase();

		logger.info("Le système d'exploitation retourné par le système est : " + osName);

		if (osName.contains("windows".toLowerCase())) {
			this.isWindows = true;
		} else {
			// TODO: implémenter linux ou autre.
		}
		
		return this.isWindows;
	}
}