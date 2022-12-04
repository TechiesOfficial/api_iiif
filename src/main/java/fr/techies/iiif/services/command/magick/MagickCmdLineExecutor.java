package fr.techies.iiif.services.command.magick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.utils.ImageFileUtil;
import fr.techies.iiif.services.command.ImageMagickExecutableFinder;

@Service
public class MagickCmdLineExecutor {

	@Autowired
	private ImageMagickExecutableFinder executableFinder;

	@Autowired
	private GenericCommandLineExecutor commandLineExecutor;

	public byte[] magick(String inFileName, String outFileName, String region, String size, String rotation,
			String quality, String format) throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append(this.executableFinder.getMagickExecutable());
		sb.append(" ");
		sb.append(this.manageRegion(region));
		sb.append(" ");
		sb.append(inFileName);
		sb.append(" ");
		sb.append(outFileName);

		this.commandLineExecutor.exec(sb.toString());

		return ImageFileUtil.getImageAsBytes(outFileName, ExtensionEnum.valueOf(format));
	}

	private StringBuilder manageRegion(String region) {

		Pattern pixelPattern = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+)");
		Pattern pctPattern =Pattern.compile("pct:\\d+,\\d+,\\d+,\\d+");
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;
		int pixelX = -1;
		int pixelY = -1;
		int pixelW = -1;
		int pixelH = -1;

		switch (region) {
		case "full":
			//Rien à faire
			break;
		case "square":
			//On récupére les dimensions de l'image via identify.exe et on applique 
			break;
		default:
			if (pixelPattern.matcher(region).matches()) {
				
				matcher=pixelPattern.matcher(region);
				
				matcher.find();
				
				pixelX = Integer.parseInt(matcher.group(1));
				pixelY = Integer.parseInt(matcher.group(2));
				pixelW = Integer.parseInt(matcher.group(3));
				pixelH = Integer.parseInt(matcher.group(4));

				sb.append("-extract " + pixelW + "x" + pixelH + "+" + pixelX + "+" + pixelY);
			}
			
			if(pctPattern.matcher(region).matches()) {
				
			}
			
			//Il est normalement impossible de ne pas être dans un des deux cas ci-dessus car
			//les paramètres sont validés en amont dès que la requête est récupérée par le controller web.
				
			break;
		}

	return sb;
}
}
