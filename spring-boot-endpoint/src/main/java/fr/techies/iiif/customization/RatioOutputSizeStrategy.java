package fr.techies.iiif.customization;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class RatioOutputSizeStrategy implements IOutputSizeStrategy {

	private Logger logger = LogManager.getLogger(RatioOutputSizeStrategy.class);

	@Value("imageapi.outputSize.strategy.ratio")
	private String ratio;

	private long ratioAsLong;

	@PostConstruct
	private void postConstruct() {
		try {
			this.ratioAsLong = Long.valueOf(this.ratio);
		} catch (NumberFormatException e) {
			logger.error("");
		}
	}

	@Override
	public String getMaxWidth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMaxHeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getMaxArea() {
		// TODO Auto-generated method stub
		return null;
	}
}
