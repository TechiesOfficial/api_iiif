package fr.techies.iiif.api.imageapi.imagerequest.model;

import fr.techies.iiif.api.imageapi.imagerequest.model.enums.RegionEnum;

public class Region {

	private RegionEnum regionEnum;

	private RegionPixel regionPixel;

	private RegionPCT regionPCT;

	public Region(RegionEnum regionEnum) {
		this(regionEnum, null, null);
	}
	
	public Region(RegionEnum regionEnum, RegionPixel regionPixel, RegionPCT regionPCT) {

		this.regionEnum = regionEnum;

		switch (regionEnum) {
		case pixel:
			this.regionPixel = regionPixel;
			break;
		case pct:
			this.regionPCT = regionPCT;
			break;
		default:
			break;
		}
	}

	public RegionEnum getRegionEnum() {
		return regionEnum;
	}

	public RegionPixel getRegionPixel() {
		return regionPixel;
	}

	public RegionPCT getRegionPCT() {
		return regionPCT;
	}
}
