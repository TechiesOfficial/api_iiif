package fr.techies.iiif.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.services.os.OSDiscoveringService;

/**
 * Implémentation basique d'un scanner de fichier pour les images.
 * 
 * Le format attendu du repertoire des images est :
 * -racine (variable path.dir actuellement)
 * 		|-id1 (peu importe)
 * 			|-page1
 * 			|-page2
 * 			|-...
 * 		|-id2
 * 			|-page1
 * 			|-page2
 * 			|-...
 * 		|-...
 * 
 * 
 * TODO: ajouter la possibilité de spécifier plusieurs racines.
 *
 */
@Service
public class AutoDiscoverImagesFromPathService {

	private Logger logger = LoggerFactory.getLogger(OSDiscoveringService.class);
	
	@Value("${iiif.dir.path}")
	private String dirPath;
	
	private Map<String, Path> fileFromId = new HashMap<String, Path>();

	@PostConstruct
	public void discoverImages() {

		try {
			FileVisitorImpl fileVisitorImpl = new FileVisitorImpl(this.fileFromId);
			Path path = Path.of(dirPath);
			
			Files.walkFileTree(path, fileVisitorImpl);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getFile(String idAndPage) {
		
		return this.fileFromId.get(idAndPage).toString();
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
