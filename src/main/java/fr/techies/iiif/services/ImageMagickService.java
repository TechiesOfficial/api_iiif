package fr.techies.iiif.services;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.utils.ImageFileUtil;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.model.RequestsIIIFBean;
import fr.techies.iiif.services.os.OSDiscoveringService;

@Service
public class ImageMagickService {

	private Logger logger = LoggerFactory.getLogger(ImageMagickService.class);

	@Autowired
	private ResourceLoader resourceLoader;

	@Autowired
	private OSDiscoveringService osDiscoveringService;

	@Autowired
	private AutoDiscoverImagesFromPathService autoDiscoverImagesFromPathService;

	@Value("${iiif.dir.path}")
	private String dirPath;

	private String pathImage;

	private String pathTmp;

	public String getMagickExecutable() throws IOException {

		Resource resource = resourceLoader.getResource("classpath:magick/win/magick.exe");
		String exePath = null;

		try {
			if (!resource.exists()) {
				logger.error("Impossible de trouver le chemin vers l'exécutable magick.exe");
			} else {
				exePath = resource.getFile().getAbsolutePath();
				logger.info("Le path vers l'exécutable magick.exe est : " + exePath);
			}
		} catch (IOException e) {
			// Une erreur non prévue. On rethrow pour tout arrêter
			throw e;
		}

		return exePath;
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
	 * @throws IOException
	 */
	private String buildImageMagickCmd(RequestsIIIFBean iiifRequests) throws IOException {

		StringBuilder cmd = new StringBuilder(this.getMagickExecutable());

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
		cmd.append(" ");
		try {
			cmd.append(this.autoDiscoverImagesFromPathService.getPath(iiifRequests.getId()));
		} catch (ImageNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ROTATION
		if (rotation != null && rotation > 0) {
			cmd.append(" -rotate " + rotation);
		}

		// Ecriture du path total vers le tmp de sortie
		this.pathTmp = dirPath + "/" + "tmp/" + imgName + "." + extension;
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
			if (osDiscoveringService.isWindows()) {
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
