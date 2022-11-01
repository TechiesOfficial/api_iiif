package fr.techies.iiif.services.os;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OSDiscoveringService {

	private Logger logger = LoggerFactory.getLogger(OSDiscoveringService.class);
	
	private boolean isWindows = true;
	
	@PostConstruct
	private void init() {
		
		String osName = System.getProperty("os.name").toLowerCase();
		
		logger.info("Le système d'exploitation retourné par le système est : " + osName);
		
		if(osName.contains("windows".toLowerCase())) {
			this.isWindows = true;
		}
		else {
			
			//TODO: implémenter linux ou autre.
		}
	}
	
	public boolean isWindows() {
		
		return this.isWindows;
	}
}