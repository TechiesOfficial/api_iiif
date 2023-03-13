package fr.techies.iiif.api.imageapi.imagerequest.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.techies.iiif.api.imageapi.common.model.Identifier;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidFormatException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidImageRequestException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidQualityException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidRegionException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidRotationException;
import fr.techies.iiif.api.imageapi.imagerequest.exception.InvalidSizeException;
import fr.techies.iiif.api.imageapi.imagerequest.model.Format;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.api.imageapi.imagerequest.model.Quality;
import fr.techies.iiif.api.imageapi.imagerequest.model.Region;
import fr.techies.iiif.api.imageapi.imagerequest.model.RegionPCT;
import fr.techies.iiif.api.imageapi.imagerequest.model.RegionPixel;
import fr.techies.iiif.api.imageapi.imagerequest.model.Rotation;
import fr.techies.iiif.api.imageapi.imagerequest.model.Size;
import fr.techies.iiif.api.imageapi.imagerequest.model.SizePCT;
import fr.techies.iiif.api.imageapi.imagerequest.model.SizePixel;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.FormatEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.QualityEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.RegionEnum;
import fr.techies.iiif.api.imageapi.imagerequest.model.enums.SizeEnum;

public class ImageRequestParametersValidator {

	protected final static Pattern REGION_PIXEL_PATTERN = Pattern.compile("^(\\d+),(\\d+),(\\d+),(\\d+)$");

	protected final static Pattern REGION_PCT_PATTERN = Pattern.compile("^pct:(\\d+(?:\\.\\d+)?),(\\d+(?:\\.\\d+)?),(\\d+(?:\\.\\d+)?),(\\d+(?:\\.\\d+)?)$");
	
	protected final static Pattern SIZE_PIXEL_PATTERN = Pattern.compile("^\\^?(\\d*),(\\d*)$");
	
	protected final static Pattern SIZE_PIXEL_RATIO_PATTERN = Pattern.compile("^\\^?!?(\\d+),(\\d+)$");

	protected final static Pattern SIZE_PCT_PATTERN = Pattern.compile("^\\^?pct:(\\d+(?:\\.\\d+)?)$");
	
	protected final static Pattern ROTATION_PATTERN = Pattern
			.compile("^(!)?([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)$");

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
	
	private Region validateRegion(String region) throws InvalidRegionException {

		Region regionBean = null;
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
			regionBean = new Region(RegionEnum.full);
			break;
		case "square":
			regionBean = new Region(RegionEnum.square);
			break;
		default:
			if (REGION_PIXEL_PATTERN.matcher(region).matches()) {

				matcher = REGION_PIXEL_PATTERN.matcher(region);

				matcher.find();

				pixelX = Integer.parseInt(matcher.group(1));
				pixelY = Integer.parseInt(matcher.group(2));
				pixelW = Integer.parseInt(matcher.group(3));
				pixelH = Integer.parseInt(matcher.group(4));

				regionBean = new Region(RegionEnum.pixel, new RegionPixel(pixelX, pixelY, pixelW, pixelH), null);
			}
			else if (REGION_PCT_PATTERN.matcher(region).matches()) {

				matcher = REGION_PCT_PATTERN.matcher(region);

				matcher.find();

				pctX = Double.parseDouble(matcher.group(1));
				pctY = Double.parseDouble(matcher.group(2));
				pctW = Double.parseDouble(matcher.group(3));
				pctH = Double.parseDouble(matcher.group(4));

				regionBean = new Region(RegionEnum.pct, null, new RegionPCT(pctX, pctY, pctW, pctH));
			}
			else {
				throw new InvalidRegionException("Impossible de parser le champ region, la valeur: " + region + " n'est pas reconnue");
			}
			
			break;
		} // end switch
		
		return regionBean;
	}
	
	private Size validateSize(String size) throws InvalidSizeException {

		Size sizeBean = null;
		int pixelW = -1;
		int pixelH = -1;
		double pct = -1;
		boolean allowUpscaling = false;
		boolean keepRatio = false;
		Matcher matcherPx = SIZE_PIXEL_PATTERN.matcher(size);
		Matcher matcherPxRatio = SIZE_PIXEL_RATIO_PATTERN.matcher(size);
		Matcher matcherPct = SIZE_PCT_PATTERN.matcher(size);
		
		switch (size) {
		case "full":
			sizeBean = new Size(SizeEnum.full, false, false);
			break;
		case "max":
			sizeBean = new Size(SizeEnum.max, false, false);
			break;
		case "^max":
			sizeBean = new Size(SizeEnum.max, true, false);
			break;
		default:
			// On vérifie si ça commence par '^'
			if(!size.isBlank() && size.charAt(0) == '^') {
				allowUpscaling = true;
			}
			
			if (matcherPx.matches()) {
				String stringW = matcherPx.group(1);
				String stringH = matcherPx.group(2);
				
				if(!stringW.isBlank())
					pixelW = Integer.parseInt(stringW);
				
				if(!stringH.isBlank())
					pixelH = Integer.parseInt(stringH);
				
				sizeBean = new Size(SizeEnum.pixel, new SizePixel(pixelW, pixelH), null, allowUpscaling, false);
			}
			else if(matcherPxRatio.matches()) {
				// le '!' ne peut être seulement dans les 2 cas suivants : "!w,h" et "^!w,h"
				// (il faut forcément les deux valeurs w et h)
				if(size.contains("!")) {
					keepRatio = true;
				}
				
				pixelW = Integer.parseInt(matcherPxRatio.group(1));
				pixelH = Integer.parseInt(matcherPxRatio.group(2));
				
				sizeBean = new Size(SizeEnum.pixel, new SizePixel(pixelW, pixelH), null, allowUpscaling, keepRatio);
			}
			else if(matcherPct.matches()) {
				pct = Double.parseDouble(matcherPct.group(1));
				
				sizeBean = new Size(SizeEnum.pct, null, new SizePCT(pct), allowUpscaling, false);
			}
			else {
				throw new InvalidSizeException("Impossible de parser le champ size, la valeur: " + size + " n'est pas reconnue");
			}
			
			break;
		} // end switch
		
		return sizeBean;
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

}
