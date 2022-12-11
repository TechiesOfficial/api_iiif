package fr.techies.iiif.lib;

import java.util.regex.Pattern;

public abstract class AIIIFRequestManager {

	protected final static Pattern ROTATION_PATTERN = Pattern.compile("^(!)?([0-9]|[1-9][0-9]|[12][0-9]{2}|3[0-5][0-9]|360)$");
	
	protected final static Pattern REGION_PIXEL_PATTERN = Pattern.compile("(\\d+),(\\d+),(\\d+),(\\d+)");
	
	protected final static Pattern REGION_PCT_PATTERN = Pattern.compile("pct:(\\d+),(\\d+),(\\d+),(\\d+)");

	protected final static Pattern QUALITY_PATTERN = Pattern.compile("^(color|gray|bitonal|default)$");
	
	protected final static Pattern FORMAT_PATTERN = Pattern.compile("^(jpg|tif|png|gif|jp2|pdf|webp)$");
	
}
