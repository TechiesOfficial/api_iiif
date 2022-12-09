package fr.techies.iiif.common;

import java.util.regex.Pattern;

public abstract class AIIIFRequestManager {

	protected static Pattern ROTATION_PATTERN = Pattern.compile("^(!)?([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)$");
	
	protected static Pattern REGION_PIXEL_PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+)");
	
	protected static Pattern REGION_PCT_PATTER = Pattern.compile("pct:(\\d+),(\\d+),(\\d+),(\\d+)");

	protected static Pattern QUALITY_PATTERN = Pattern.compile("");
	
	protected static Pattern FORMAT_PATTER = Pattern.compile("");
	
}
