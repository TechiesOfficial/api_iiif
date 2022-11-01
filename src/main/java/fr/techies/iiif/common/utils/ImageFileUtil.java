package fr.techies.iiif.common.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import org.apache.tomcat.util.http.fileupload.FileUtils;

import fr.techies.iiif.common.enums.ExtensionEnum;

public class ImageFileUtil {

	private static final String TMP_DIR = "/tmp";

	/**
	 * Récupère une image et la retourne en byte[]
	 * 
	 * @param pathImg   - path total de l'image
	 * @param extension - extension demandée
	 * @return
	 */
	public static byte[] getImageAsBytes(String pathImg, ExtensionEnum extension) {

		byte[] image = null;
		BufferedImage bImg = null;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		File fImg = null;

		try {
			// Récupération de l'image via l'objet File
			fImg = new File(pathImg);

			// Lecture depuis un BufferedImage
			bImg = ImageIO.read(fImg);

			// Conversion en tableau de bytes
			ImageIO.write(bImg, extension.toString(), output);
			image = output.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return image;
	}

	/**
	 * Permet de créer un dossier temporaire /tmp
	 * 
	 * @param dirPath - dossier dans lequel se trouvera le dossier /tmp
	 * @throws IOException
	 */
	public static void createTmpDir(String dirPath) throws IOException {

		String dirTmpPath = dirPath + TMP_DIR;

		try {
			Path path = Paths.get(dirTmpPath);
			if (!path.toFile().exists())
				Files.createDirectory(path);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erreur à la création du dir : " + dirTmpPath);
		}
	}

	/**
	 * Permet de supprimer le dossier /tmp
	 * 
	 * @param dirPath - dossier dans lequel se trouve le dossier /tmp
	 * @throws IOException
	 */
	public static void removeTmpDir(String dirPath) throws IOException {

		String dirTmpPath = dirPath + TMP_DIR;

		try {
			File tmpDir = new File(dirTmpPath);
			FileUtils.deleteDirectory(tmpDir);

		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException("Erreur à la suppression du dir : " + dirTmpPath);
		}
	}
}
