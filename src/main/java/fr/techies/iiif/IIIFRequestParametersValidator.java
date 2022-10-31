package fr.techies.iiif;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class IIIFRequestParametersValidator {

	public List<String> validateParameters(String id, String page, String region, String size, String rotation,
			String quality, String format) {

		List<String> errors = new ArrayList<>();

		errors.addAll(this.validateIDandPage());
		errors.addAll(this.validateRegion(region));
		errors.addAll(this.validateSize(size));
		errors.addAll(this.validateRotation(rotation));
		errors.addAll(this.validateQuality(quality));

		return errors;
	}

	protected abstract List<String> validateIDandPage();

	private List<String> validateQuality(String quality) {
		List<String> errors = new ArrayList<>();
		String pixelPattern = new String();
		String pctPatterN = new String();

		return errors;
	}

	private List<String> validateRotation(String rotation) {

		String pattern = new String("!?\\b([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)\\b");
		List<String> errors = new ArrayList<>();

		return errors;
	}

	private List<String> validateSize(String size) {

		String pattern = new String("!?\\b([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)\\b");
		List<String> errors = new ArrayList<>();

		return errors;
	}

	private List<String> validateRegion(String region) {

		List<String> errors = new ArrayList<>();
		String pixelPattern = new String();
		String pctPattern = new String();

		switch (region) {
		case "full":
			break;
		case "square":
			break;
		default:
			if (!Pattern.matches(pixelPattern, pctPattern) && !Pattern.matches(region, pctPattern)) {
				errors.add("Impossible de parser le champ region, la valeur: " + region + "n'est pas reconnue");
			}

			break;
		}

		return errors;
	}
}
