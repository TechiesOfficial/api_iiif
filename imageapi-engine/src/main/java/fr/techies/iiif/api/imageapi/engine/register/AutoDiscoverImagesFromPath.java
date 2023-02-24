package fr.techies.iiif.api.imageapi.engine.register;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import fr.techies.iiif.common.exception.ImageNotFoundException;

/**
 * Implémentation basique d'un scanner de fichier pour les images.
 * 
 * Mets en cache dans une hashmap tous les fichiers.
 * 
 * TODO: ajouter la possibilité de spécifier plusieurs racines. TODO: une fois
 * la liste dOes types d'images officiellement gérée définie, ne prendre en
 * compte que ces fichiers.
 *
 */
public class AutoDiscoverImagesFromPath implements ImageRegister {

	private Logger logger = LogManager.getLogger(AutoDiscoverImagesFromPath.class);

	private Map<String, Path> fileFromId = new HashMap<String, Path>();

	public AutoDiscoverImagesFromPath(Path path) {
		try {
			FileVisitorImpl fileVisitorImpl = new FileVisitorImpl(this.fileFromId);

			Files.walkFileTree(path, fileVisitorImpl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Path getPath(String identifier) throws ImageNotFoundException {

		if (this.fileFromId.get(identifier) != null)
			return this.fileFromId.get(identifier);

		throw new ImageNotFoundException("L'image " + identifier + " n'a pas été trouvée");
	}

	private class FileVisitorImpl implements FileVisitor<Path> {

		private Map<String, Path> fileFromId;

		public FileVisitorImpl(Map<String, Path> fileFromId) {
			this.fileFromId = fileFromId;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			logger.debug("preVisitDirectory " + dir);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			logger.debug("visitFile " + file);

			this.fileFromId.put(file.getFileName().toString(), file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			logger.debug("visitFileFailed " + file);
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			logger.debug("postVisitDirectory " + dir);
			return FileVisitResult.CONTINUE;
		}
	}
}
