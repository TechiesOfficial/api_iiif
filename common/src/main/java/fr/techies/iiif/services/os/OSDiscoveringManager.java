package fr.techies.iiif.services.os;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class OSDiscoveringManager {

	private Logger logger = LogManager.getLogger(OSDiscoveringManager.class);

	private OSEnum osEnum = null;

	public OSDiscoveringManager() {
		String osName = System.getProperty("os.name").toLowerCase();

		logger.info("Le système d'exploitation retourné par le système est : " + osName);

		if (osName.contains("windows".toLowerCase())) {
			this.osEnum = OSEnum.Windows;
		} else {
			this.osEnum = OSEnum.Linux;
		}
	}

	public OSEnum getOsEnum() {
		return osEnum;
	}
}