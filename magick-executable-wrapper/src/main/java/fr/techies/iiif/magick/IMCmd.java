package fr.techies.iiif.magick;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * 
 * <p>
 * <ul>
 * <li>one or more required input filenames.</li>
 * <li>zero, one, or more image settings.</li>
 * <li>zero, one, or more image operators.</li>
 * <li>zero, one, or more image sequence operators.</li>
 * <li>zero, one, or more image stacks.</li>
 * <li>zero or one output image filenames (required by magick, convert,
 * composite, montage, compare, import, conjure).
 * </p>
 * 
 * @see https://imagemagick.org/script/command-line-processing.php
 */
public class IMCmd {

	private List<File> inputFiles = new ArrayList<File>();
	
	private List<String> imageSettings = new ArrayList<String>();
	
	private List<String> imageOperatos = new ArrayList<String>();
	
	private List<String> imageSequenceOperators = new ArrayList<String>();
	
	private List<String> imageStacks = new ArrayList<String>();
	
	private List<File> ouputFiles = new ArrayList<File>();
	
	public void setInputFiles(File ... files) {
		this.inputFiles.addAll(Arrays.asList(files));
	}
	
}
