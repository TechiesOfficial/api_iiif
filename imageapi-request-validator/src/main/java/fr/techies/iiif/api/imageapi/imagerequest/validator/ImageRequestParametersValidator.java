package fr.techies.iiif.api.imageapi.imagerequest.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.imagerequest.model.Format;
import fr.techies.iiif.api.imageapi.imagerequest.model.Identifier;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.Quality;
import fr.techies.iiif.api.imageapi.imagerequest.model.Region;
import fr.techies.iiif.api.imageapi.imagerequest.model.RegionPCT;
import fr.techies.iiif.api.imageapi.imagerequest.model.RegionPixel;
import fr.techies.iiif.api.imageapi.imagerequest.model.Rotation;
import fr.techies.iiif.api.imageapi.imagerequest.model.Size;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.QualityEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.RegionEnum;

@Service
public class ImageRequestParametersValidator {

	protected final static Pattern ROTATION_PATTERN = Pattern
			.compile("^(!)?([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)$");

	protected final static Pattern REGION_PIXEL_PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+)");

	protected final static Pattern REGION_PCT_PATTERN = Pattern.compile("pct:(\\d+),(\\d+),(\\d+),(\\d+)");

	protected final static Pattern QUALITY_PATTERN = Pattern.compile("^(color|gray|bitonal|default)$");

	protected final static Pattern FORMAT_PATTERN = Pattern.compile("^(jpg|tif|png|gif|jp2|pdf|webp)$");

	public ImageRequest validateParameters(String identifier, String region, String size, String rotation,
			String quality, String format) throws InvalidImageRequestException {

		ImageRequest imageRequest = new ImageRequest();

		imageRequest.setIdentifier(new Identifier(identifier));
		imageRequest.setRegion(this.validateRegion(region));
		imageRequest.setSize(this.validateSize(size));
		imageRequest.setRotation(this.validateRotation(rotation));
		imageRequest.setQuality(this.validateQuality(quality));
		imageRequest.setFormat(this.validateFormat(format));

		return imageRequest;
	}

	private Format validateFormat(String format) throws InvalidFormatException {

		FormatEnum formatEnum = null;

		Matcher matcher = null;

		if (FORMAT_PATTERN.matcher(format).matches()) {

			matcher = FORMAT_PATTERN.matcher(format);

			matcher.find();

			formatEnum = FormatEnum.valueOf(matcher.group(1));
		} else
			throw new InvalidFormatException(
					"Impossible de parser le champ format, la valeur: " + format + " n'est pas reconnue");

		return new Format(formatEnum);
	}

	private Quality validateQuality(String quality) throws InvalidQualityException {

		QualityEnum qualityEnum = null;
		Matcher matcher = null;
		if (QUALITY_PATTERN.matcher(quality).matches()) {

			matcher = QUALITY_PATTERN.matcher(quality);

			matcher.find();

			qualityEnum = QualityEnum.valueOfQuality(matcher.group(1));
		} else
			throw new InvalidQualityException(
					"Impossible de parser le champ quality, la valeur: " + quality + " n'est pas reconnue");

		return new Quality(qualityEnum);
	}

	private Rotation validateRotation(String rotation) throws InvalidRotationException {

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
		} else
			throw new InvalidRotationException(
					"Impossible de parser le champ rotation, la valeur: " + rotation + " n'est pas reconnue");

		return new Rotation(mirroring, degree);
	}

	private Size validateSize(String size) {

		// TODO: Revoir l'expression régulière car il est possible de ne mettre qu'une
		// virgule toute seule sans chiffres. Pas très grave, c'est un cas très tordu.
		String pixelPattern = new String("^\\^?!?\\d*,\\d*$");
		String pctPattern = new String("^\\^?pct:\\d+$");
		List<String> errors = new ArrayList<>();

		switch (size) {
		case "max":
			break;
		case "^max":
			break;
		default:
			if (!Pattern.matches(pixelPattern, size) && !Pattern.matches(pctPattern, size)) {
				errors.add("Impossible de parser le champ size, la valeur: " + size + " n'est pas reconnue");
			}

			break;
		}

		return null;
	}

	private Region validateRegion(String region) throws InvalidRegionException {

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
			return new Region(RegionEnum.full);
		case "square":
			return new Region(RegionEnum.square);
		default:
			if (REGION_PIXEL_PATTERN.matcher(region).matches()) {

				matcher = REGION_PIXEL_PATTERN.matcher(region);

				matcher.find();

				pixelX = Integer.parseInt(matcher.group(1));
				pixelY = Integer.parseInt(matcher.group(2));
				pixelW = Integer.parseInt(matcher.group(3));
				pixelH = Integer.parseInt(matcher.group(4));

				return new Region(RegionEnum.pixel, new RegionPixel(pixelX, pixelY, pixelW, pixelH), null);
			}			// TODO: revoir l'exp reg et finir
			else if (REGION_PCT_PATTERN.matcher(region).matches()) {

				matcher = REGION_PCT_PATTERN.matcher(region);

				matcher.find();

				pctX = Double.parseDouble(matcher.group(1));
				pctY = Double.parseDouble(matcher.group(2));
				pctW = Double.parseDouble(matcher.group(3));
				pctH = Double.parseDouble(matcher.group(4));

				return new Region(RegionEnum.pct, null, new RegionPCT(pctX, pctY, pctW, pctH));
			}
			else 
				throw new InvalidRegionException("Impossible de parser le champ region, la valeur: " + region + " n'est pas reconnue");

		}
	}
}
