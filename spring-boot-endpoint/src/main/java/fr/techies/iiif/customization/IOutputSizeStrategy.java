package fr.techies.iiif.customization;

/**
 * <p>Interface used to customize output width and height in pixel.</p>
 * <p>Purpose is to allow the possibility of restraining image output size in oder, for example, to minimize network problem.</p>
 */
public interface IOutputSizeStrategy {

	public String getMaxWidth();

	public String getMaxHeight();

	public String getMaxArea();
}
