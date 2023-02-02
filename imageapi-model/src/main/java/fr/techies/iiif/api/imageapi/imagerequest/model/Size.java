package fr.techies.iiif.api.imageapi.imagerequest.model;

import fr.techies.iiif.api.imageapi.imagerequest.model.enums.SizeEnum;

public class Size {
	
	private SizeEnum sizeEnum;
	
	private SizePixel sizePixel;
	
	private SizePCT sizePCT;
	
	boolean allowUpscaling;
	
	boolean keepRatio;
	
	public Size(SizeEnum sizeEnum, boolean allowUpscaling, boolean keepRatio) {
		this(sizeEnum, null, null, allowUpscaling, keepRatio);
	}
	
	public Size(SizeEnum sizeEnum, SizePixel sizePixel, SizePCT sizePCT, boolean allowUpscaling, boolean keepRatio) {

		this.sizeEnum = sizeEnum;
		this.allowUpscaling = allowUpscaling;
		this.keepRatio = keepRatio;

		switch (sizeEnum) {
		case pixel:
			this.sizePixel = sizePixel;
			break;
		case pct:
			this.sizePCT = sizePCT;
			break;
		default:
			break;
		}
	}

	public SizeEnum getSizeEnum() {
		return sizeEnum;
	}

	public SizePixel getSizePixel() {
		return sizePixel;
	}

	public SizePCT getSizePCT() {
		return sizePCT;
	}

	public boolean isAllowUpscaling() {
		return allowUpscaling;
	}
	
	public boolean isKeepRatio() {
		return keepRatio;
	}
}
