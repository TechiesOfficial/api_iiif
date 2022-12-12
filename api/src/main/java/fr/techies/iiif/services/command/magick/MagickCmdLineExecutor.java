package fr.techies.iiif.services.command.magick;

import java.io.IOException;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.lib.AIIIFRequestManager;
import fr.techies.iiif.lib.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.lib.enums.FormatEnum;
import fr.techies.iiif.lib.enums.QualityEnum;
import fr.techies.iiif.lib.utils.ImageFileUtil;
import fr.techies.iiif.services.command.ImageMagickExecutableFinder;
import fr.techies.iiif.services.command.identify.IdentifyCmdLineExecutor;
import fr.techies.iiif.services.command.identify.IdentifyResultBean;

@Service
public class MagickCmdLineExecutor extends AIIIFRequestManager{

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
		// A priori extract doit se trouver avant le fichier en entrée
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
		sb.append(this.manageFormat(outFileName, format));
		

		this.commandLineExecutor.exec(sb.toString());

		return ImageFileUtil.getImageAsBytes(outFileName+"."+FormatEnum.valueOf(format), FormatEnum.valueOf(format));
	}

	private StringBuilder manageFormat(String outFileName, String format) {

		StringBuilder sb = new StringBuilder();
		FormatEnum formatEnum = null;		
		Matcher matcher = null;
		if (FORMAT_PATTERN.matcher(format).matches()) {

			matcher = FORMAT_PATTERN.matcher(format);

			matcher.find();

			formatEnum = FormatEnum.valueOf(matcher.group(1));
			
			sb.append(outFileName).append(".").append(formatEnum.toString());
		}
		
		return sb;
	}

	private StringBuilder manageQuality(String quality) {

		StringBuilder sb = new StringBuilder();
		QualityEnum qualityEnum = null;		
		Matcher matcher = null;
		if (QUALITY_PATTERN.matcher(quality).matches()) {

			matcher = QUALITY_PATTERN.matcher(quality);

			matcher.find();

			qualityEnum = QualityEnum.valueOfQuality(matcher.group(1));

			switch (qualityEnum) {
			case bitonal:
				sb.append("-monochrome ");
				break;
			case gray:
				sb.append("-colorspace ").append("Gray ");
				break;
				
			default:
				break;
			}
			
			
		}
		
		return sb;
	}

	private StringBuilder manageRotation(String rotation) {

		StringBuilder sb = new StringBuilder();
		Matcher matcher = null;
		int degree = -1;
		boolean mirroring = false;

		if (ROTATION_PATTERN.matcher(rotation).matches()) {

			matcher = ROTATION_PATTERN.matcher(rotation);

			matcher.find();

			// ! est la seule valeure possible, donc si pas null alors mirroring
			if (matcher.group(1) != null)
				mirroring = true;

			degree = Integer.valueOf(matcher.group(2));

			if (mirroring)
				sb.append("-transpose ");

			//A priori c'est comme cela que l'on fait de la transparence mais ca met un fond noir!
			//Testé sur chrome et ie
			if(degree != 0)
				sb.append("-background 'rgba(0,0,0,0)' -rotate " + degree + " +repage");
		}

		return sb;
	}

	private StringBuilder manageSize(String size) {

		StringBuilder sb = new StringBuilder();

		return sb;
	}

	private StringBuilder manageRegion(String region, IdentifyResultBean identifyResultBean) {

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
			if (REGION_PIXEL_PATTERN.matcher(region).matches()) {

				matcher = REGION_PIXEL_PATTERN.matcher(region);

				matcher.find();

				pixelX = Integer.parseInt(matcher.group(1));
				pixelY = Integer.parseInt(matcher.group(2));
				pixelW = Integer.parseInt(matcher.group(3));
				pixelH = Integer.parseInt(matcher.group(4));

				sb.append("-extract " + pixelW + "x" + pixelH + "+" + pixelX + "+" + pixelY);
			}
			// TODO: revoir l'exp reg et finir
			if (REGION_PCT_PATTERN.matcher(region).matches()) {

				matcher = REGION_PCT_PATTERN.matcher(region);

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
