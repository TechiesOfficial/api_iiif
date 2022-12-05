package fr.techies.iiif.services.command.magick;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.utils.ImageFileUtil;
import fr.techies.iiif.services.command.ImageMagickExecutableFinder;
import fr.techies.iiif.services.command.identify.IdentifyCmdLineExecutor;
import fr.techies.iiif.services.command.identify.IdentifyResultBean;

@Service
public class MagickCmdLineExecutor {

	@Autowired
	private ImageMagickExecutableFinder executableFinder;

	@Autowired
	private GenericCommandLineExecutor commandLineExecutor;

	@Autowired
	private IdentifyCmdLineExecutor identifyCmdLineExecutor;

	public byte[] magick(String inFileName, String outFileName, String region, String size, String rotation,
			String quality, String format) throws IOException {

		IdentifyResultBean identifyResultBean = null;
		StringBuilder sb = new StringBuilder();

		identifyResultBean = this.identifyCmdLineExecutor.identify(inFileName);

		sb.append(this.executableFinder.getMagickExecutable());
		sb.append(" ");
		//A priori extract doit se trouver avant le fichier en entrée
		sb.append(this.manageRegion(region, identifyResultBean));
		sb.append(" ");
		sb.append(inFileName);
		sb.append(" ");
		sb.append(this.manageSize(size));
		sb.append(" ");
		sb.append(this.manageRotation(rotation));
		sb.append(" ");
		sb.append(this.manageQuality(quality));
		sb.append(" ");
		sb.append(this.manageFormat(quality));
		sb.append(" ");
		sb.append(outFileName);

		this.commandLineExecutor.exec(sb.toString());

		return ImageFileUtil.getImageAsBytes(outFileName, ExtensionEnum.valueOf(format));
	}

	private StringBuilder manageFormat(String quality) {

		StringBuilder sb = new StringBuilder();

		return sb;
	}

	private StringBuilder manageQuality(String quality) {

		StringBuilder sb = new StringBuilder();

		return sb;
	}

	private StringBuilder manageRotation(String rotation) {

		Pattern pattern = Pattern.compile("^(!)?([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)$");
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;
		int degree = -1;
		boolean mirroring = false;

		if (pattern.matcher(rotation).matches()) {

			matcher = pattern.matcher(rotation);

			matcher.find();
			
			//! est la seule valeure possible, donc si pas null alors mirroring
			mirroring = matcher.group(1)!= null;
			
			degree = Integer.valueOf(matcher.group(2));
			
			if(mirroring)
				sb.append("-transpose ");
			
			sb.append("-rotate " + degree);
		}

		return sb;
	}

	private StringBuilder manageSize(String size) {

		StringBuilder sb = new StringBuilder();

		return sb;
	}

	private StringBuilder manageRegion(String region, IdentifyResultBean identifyResultBean) {

		Pattern pixelPattern = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+)");
		Pattern pctPattern = Pattern.compile("pct:(\\d+),(\\d+),(\\d+),(\\d+)");
		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;
		int pixelX = -1;
		int pixelY = -1;
		int pixelW = -1;
		int pixelH = -1;
		double pctX = -1;
		double pctY = -1;
		double pctW = -1;
		double pctH = -1;

		switch (region) {
		case "full":
			// Rien à faire
			break;
		case "square":
			// On récupére les dimensions de l'image via identify.exe et on applique
			break;
		default:
			if (pixelPattern.matcher(region).matches()) {

				matcher = pixelPattern.matcher(region);

				matcher.find();

				pixelX = Integer.parseInt(matcher.group(1));
				pixelY = Integer.parseInt(matcher.group(2));
				pixelW = Integer.parseInt(matcher.group(3));
				pixelH = Integer.parseInt(matcher.group(4));

				sb.append("-extract " + pixelW + "x" + pixelH + "+" + pixelX + "+" + pixelY);
			}
			// TODO: revoir l'exp reg et finir
			if (pctPattern.matcher(region).matches()) {

				matcher = pctPattern.matcher(region);

				matcher.find();

				pctX = Double.parseDouble(matcher.group(1));
				pctY = Double.parseDouble(matcher.group(2));
				pctW = Double.parseDouble(matcher.group(3));
				pctH = Double.parseDouble(matcher.group(4));

				pixelX = (int) (Integer.valueOf(identifyResultBean.getHeight()) * pctX);

				sb.append("-extract " + pixelW + "x" + pixelH + "+" + pixelX + "+" + pixelY);
			}

			// Il est normalement impossible de ne pas être dans un des deux cas ci-dessus
			// car
			// les paramètres sont validés en amont dès que la requête est récupérée par le
			// controller web.

			break;
		}

		return sb;
	}
}
