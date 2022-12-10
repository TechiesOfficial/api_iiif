package fr.techies.iiif.controller.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.techies.iiif.common.AIIIFRequestManager;
import fr.techies.iiif.common.enums.FormatEnum;
import fr.techies.iiif.exception.ImageNotFoundException;
import fr.techies.iiif.services.image.register.AutoDiscoverImagesFromPathService;

@Service
public class ImageRequestParametersValidator extends AIIIFRequestManager {

	
	public List<String> validateParameters(String identifier, String region, String size, String rotation, String quality,
			String format) {

		List<String> errors = new ArrayList<>();

		errors.addAll(this.validateRegion(region));
		errors.addAll(this.validateSize(size));
		errors.addAll(this.validateRotation(rotation));
		errors.addAll(this.validateQuality(quality));
		errors.addAll(this.validateFormat(format));
		
		return errors;
	}

	private List<String> validateFormat(String format) {

		FormatEnum formatEnum = null;
		List<String> errors = new ArrayList<>();
		
		try {
			formatEnum = FormatEnum.valueOf(format);
		} catch (IllegalArgumentException e) {
			errors.add("Impossible de parser le champ format, la valeur: " + format + " n'est pas reconnue");
		}
		
		return errors;
	}

	private List<String> validateQuality(String quality) {
		List<String> errors = new ArrayList<>();

		switch (quality) {
		case "color":
			break;
		case "gray":
			break;
		case "bitonal":
			break;
		case "default":
			break;
		default:
			errors.add("Impossible de parser le champ quality, la valeur: " + quality + " n'est pas reconnue");
			break;
		}
		
		return errors;
	}

	private List<String> validateRotation(String rotation) {

		String pattern = new String("!?\\b([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)\\b");
		List<String> errors = new ArrayList<>();

		if(!Pattern.matches(pattern, rotation)) {
		
			errors.add("Impossible de parser le champ rotation, la valeur: " + rotation + " n'est pas reconnue");
		}
		
		return errors;
	}

	private List<String> validateSize(String size) {

		//TODO: Revoir l'expression régulière car il est possible de ne mettre qu'une virgule toute seule sans chiffres. Pas très grave, c'est un cas très tordu.
		String pixelPattern = new String("^\\^?!?\\d*,\\d*$");
		String pctPattern = new String("^\\^?pct:\\d+$");
		List<String> errors = new ArrayList<>();

		switch (size) {
		case "max":
			break;
		case "^max":
			break;
		default:
			if (!Pattern.matches(pixelPattern, size) && !Pattern.matches(pctPattern, size))  {
				errors.add("Impossible de parser le champ size, la valeur: " + size + " n'est pas reconnue");
			}

			break;
		}
		
		return errors;
	}

	private List<String> validateRegion(String region) {

		List<String> errors = new ArrayList<>();
		// TODO: gerer le fait que 0 ne soit probablement pas une bonne valeur
		String pixelPattern = new String("\\d+,\\d+,\\d+,\\d+");
		String pctPattern = new String("pct:\\d+.?\\d+,\\d+.?\\d+,\\d+.?\\d+,\\d+.?\\d+");

		switch (region) {
		case "full":
			break;
		case "square":
			break;
		default:
			if (!Pattern.matches(pixelPattern, region) && !Pattern.matches(pctPattern, region)) {
				errors.add("Impossible de parser le champ region, la valeur: " + region + " n'est pas reconnue");
			}
			break;
		}
		return errors;
	}
}
