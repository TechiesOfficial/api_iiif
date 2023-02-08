package fr.techies.iiif.rest.services;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import fr.techies.iiif.services.os.OSDiscoveringManager;
import fr.techies.iiif.services.os.OSEnum;

@Service
public class AutoDiscoverOSService {

	OSDiscoveringManager osDiscoveringManager = null;
	
	@PostConstruct
	private void PostConstruct() {
		this.osDiscoveringManager = new OSDiscoveringManager();
	}
	
	public OSEnum getOS() {
		return this.osDiscoveringManager.getOsEnum();
	}
}
