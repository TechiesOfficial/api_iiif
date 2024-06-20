package fr.techies.iiif.api.imageapi.services.command.magick;

public class IdentifyFormatParameterService {

	/**
	 * Filesize, FilenameExtension, FileName, Witdth, Height, xRes, yRes
	 */
	public String build() {

		StringBuilder sb = new StringBuilder();

		sb.append("-format ");
		sb.append("\"");

		sb.append("%b");
		sb.append(",%e");
		sb.append(",%f");
		sb.append(",%w");
		sb.append(",%h");
		sb.append(",%x");
		sb.append(",%y");

		sb.append("\"");

		return sb.toString();
	}
}
