package fr.techies.iiif.lib.utils;

public class FileUtil {

	/**
	 * https://www.baeldung.com/java-filename-without-extension
	 * 
	 * @param filename
	 * @param removeAllExtensions
	 * 
	 * @return
	 */
	public static String removeFileExtension(String filename, boolean removeAllExtensions) {
		if (filename == null || filename.isEmpty()) {
			return filename;
		}

		String extPattern = "(?<!^)[.]" + (removeAllExtensions ? ".*" : "[^.]*$");
		return filename.replaceAll(extPattern, "");
	}
}
