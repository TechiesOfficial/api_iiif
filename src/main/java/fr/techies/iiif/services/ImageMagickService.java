package fr.techies.iiif.services;

import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.utils.ImageFileUtil;
import fr.techies.iiif.model.RequestsIIIFBean;

@Service
public class ImageMagickService {
	
	private boolean isWindows = false;
	
	@Value("${iiif.dir.path}")
	private String dirPath;
	
	private String pathImage;
	
	private String pathTmp;
	
	/**
	 * PostConstruct permettant de savoir si on est sur un OS windows ou linux
	 */
	@PostConstruct
	private void init() {
		
		String osName = System.getProperty("os.name").toLowerCase();
		
		this.isWindows = osName.startsWith("windows");
	}
	
	/**
	 * Gestion de l'image par ImageMagick.
	 * 
	 * @return
	 */
	public byte[] handleImage(RequestsIIIFBean iiifRequests) {
		
		byte[] image = null;
		ExtensionEnum extension = null;
		String cmd = null;
		
		try {
			// Création d'un dossier temporaire
			ImageFileUtil.createTmpDir(dirPath);
		
			// Ecriture de la commande ImageMagick à exécuter
			cmd = this.buildImageMagickCmd(iiifRequests);
			
			// Exécution de la commande ImageMagick
			this.exec(cmd);
			
			// Récupération de la nouvelle image (tmp)
			extension = iiifRequests.getFormat();
			image = ImageFileUtil.getImageAsBytes(pathTmp, extension);
			
			// Suppression du fichier tmp
			ImageFileUtil.removeTmpDir(dirPath);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	/**
	 * Construit la requête ImageMagick depuis l'objet {@link RequestsIIIFBean}
	 * 
	 * @param iiifRequests
	 * @return
	 */
	private String buildImageMagickCmd(RequestsIIIFBean iiifRequests) {
		
		StringBuilder cmd = new StringBuilder("magick ");
		
		String imgName = iiifRequests.getId();
//		int page = iiifRequests.getPage();
//		String region = iiifRequests.getRegion();
//		String size = iiifRequests.getSize();
//		boolean mirroring = isMirroring();
		Double rotation = Double.valueOf(iiifRequests.getRotation());
//		String quality = iiifRequests.getQuality();
		ExtensionEnum extension = iiifRequests.getFormat();
		
		// Ecriture du path total vers l'image en entrée
		// FIXME : '.*' trouver une meilleure solution
		this.pathImage = dirPath + imgName + ".*";
		cmd.append(this.pathImage);
		
		// ROTATION
		if(rotation != null && rotation > 0) {
			cmd.append(" -rotate " + rotation);
		}
		
		// Ecriture du path total vers le tmp de sortie
		this.pathTmp = dirPath + "tmp/" + imgName + "." + extension;
		cmd.append(" " + this.pathTmp);
		
		return cmd.toString();
	}
	
	/**
	 * Exécute la commande ImageMagick.
	 * 
	 * @param cmd - commande à lancer
	 */
	private void exec(String cmd) {
		
		ProcessBuilder builder = new ProcessBuilder();
		
		try {
			if (isWindows) {
				builder.command("cmd.exe", "/c", cmd);
			} else {
				builder.command("sh", "-c", cmd);
			}
			
			builder.directory(new File(dirPath));
			Process process = builder.start();
			
			// Attendre fin du process
			process.waitFor();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
