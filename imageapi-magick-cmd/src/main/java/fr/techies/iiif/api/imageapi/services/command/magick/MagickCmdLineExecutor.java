package fr.techies.iiif.api.imageapi.services.command.magick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import fr.techies.iiif.CommandLineExecutor;
import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.common.enums.ExtensionEnum;
import fr.techies.iiif.common.utils.ImageFileUtil;
import fr.techies.iiif.magick.IMExecutableUnpacker;

public class MagickCmdLineExecutor {

	private String unpackedTargetPath;
	
	private CommandLineExecutor commandLineExecutor;

	private IdentifyCmdLineExecutor identifyCmdLineExecutor;

	private IMExecutableUnpacker imExecutableUnpacker;
	
	public MagickCmdLineExecutor(String unpackedTargetPath) {
		this.unpackedTargetPath = unpackedTargetPath;
		this.imExecutableUnpacker = new IMExecutableUnpacker(new File(this.unpackedTargetPath));
		this.commandLineExecutor = new CommandLineExecutor();
		this.identifyCmdLineExecutor = new IdentifyCmdLineExecutor(this.unpackedTargetPath);
	}
	
	public byte[] magick(Path inFileName, Path outFileName, ImageRequest imageRequest) throws IOException {

		IdentifyResultBean identifyResultBean = null;
		StringBuilder sb = new StringBuilder();

		identifyResultBean = this.identifyCmdLineExecutor.identify(inFileName);

		sb.append(this.imExecutableUnpacker.getMagickExecutable());
		sb.append(" ");
		// A priori extract doit se trouver avant le fichier en entrée
		sb.append(this.manageRegion(imageRequest, identifyResultBean));
		sb.append(" ");
		sb.append(inFileName);
		sb.append(" ");
		sb.append(this.manageSize(imageRequest,identifyResultBean));
		sb.append(" ");
		sb.append(this.manageRotation(imageRequest));
		sb.append(" ");
		sb.append(this.manageQuality(imageRequest));
		
		ExtensionEnum extensionEnum = this.manageFormat(imageRequest);
		
		sb.append(" ");
		sb.append(outFileName);

		this.commandLineExecutor.exec(sb.toString(), this.unpackedTargetPath);
		
		return ImageFileUtil.getImageAsBytes(outFileName.toString(),
				extensionEnum);
	}
	
	private StringBuilder manageRegion(ImageRequest imageRequest, IdentifyResultBean identifyResultBean) {

		StringBuilder sb = new StringBuilder();
		
		int width = 0;
		int height = 0;
		int x = 0;
		int y = 0;
		
		int identifyWidth = Integer.parseInt(identifyResultBean.getWidth());
		int identifyHeight = Integer.parseInt(identifyResultBean.getHeight());
		
		switch (imageRequest.getRegion().getRegionEnum()) {
		case full:
			// Rien à faire
			break;
		case square:
			// On récupère les dimensions de l'image via identify.exe et on applique
			if(identifyWidth < identifyHeight) {
				width = identifyWidth;
				height = identifyWidth;
				
				y = (identifyHeight - identifyWidth)/2;
			}
			else {
				width = identifyHeight;
				height = identifyHeight;
				
				x = (identifyWidth - identifyHeight)/2;
			}
			
			break;
		case pixel:
			width = imageRequest.getRegion().getRegionPixel().getPixelW();
			height = imageRequest.getRegion().getRegionPixel().getPixelH();
			x = imageRequest.getRegion().getRegionPixel().getPixelX();
			y = imageRequest.getRegion().getRegionPixel().getPixelY();
			
			break;
		case pct:
			double pctWidth = imageRequest.getRegion().getRegionPCT().getPctW();
			double pctHeight = imageRequest.getRegion().getRegionPCT().getPctH();
			double pctX = imageRequest.getRegion().getRegionPCT().getPctX();
			double pctY = imageRequest.getRegion().getRegionPCT().getPctY();
			
			width = this.percentOf(identifyWidth, pctWidth);
			height = this.percentOf(identifyHeight, pctHeight);
			x = this.percentOf(identifyWidth, pctX);
			y = this.percentOf(identifyHeight, pctY);
			
			break;
		}
		
		sb.append("-extract " + width + "x" + height + "+" + x + "+" + y);

		return sb;
	}
	
	private StringBuilder manageSize(ImageRequest imageRequest, IdentifyResultBean identifyResultBean) {

		StringBuilder sb = new StringBuilder();
		int identifyWidth = Integer.parseInt(identifyResultBean.getWidth());
		int identifyHeight = Integer.parseInt(identifyResultBean.getHeight());
		
		boolean allowUpscaling = imageRequest.getSize().isAllowUpscaling();
		boolean keepRatio = imageRequest.getSize().isKeepRatio();
		int width = 0;
		int height = 0;
		
		
		switch (imageRequest.getSize().getSizeEnum()) {
		case full:
			// Rien à faire
			break;
			
		case max:
			// TODO : récupérer maxWidth, maxHeight etc ... du identify
			break;
			
		case pixel:
			// TODO : gérer tous les cas
			width = imageRequest.getSize().getSizePixel().getPixelW();
			height = imageRequest.getSize().getSizePixel().getPixelH();
			
			break;
			
		case pct:
			double percentage = imageRequest.getSize().getSizePCT().getPct();
			
			// TODO : pour l'instant, si on n'a pas le ^ (upscaling), tout ce qui est > 100 sera = 100
			if(percentage > 100 && !allowUpscaling) {
				percentage = 100;
			}
			
			width = this.percentOf(identifyWidth, percentage);
			height = this.percentOf(identifyHeight, percentage);
			
			break;
		}
		
		// exemple : -size 640x512 ou -size 640x512+256
		
		sb.append("-size " + width + "x" + height);

		return sb;
	}
	
	private StringBuilder manageRotation(ImageRequest imageRequest) {

		StringBuilder sb = new StringBuilder();
		boolean mirroring = imageRequest.getRotation().isMirroring();
		int degree = imageRequest.getRotation().getDegree();

		if (mirroring) {
			sb.append("-flop ");
		}

		// A priori c'est comme cela que l'on fait de la transparence mais ca met un
		// fond noir!
		// Testé sur chrome et ie
		if (degree != 0)
			sb.append("-background 'rgba(0,0,0,0)' -rotate " + degree + " +repage");

		return sb;
	}
	
	private StringBuilder manageQuality(ImageRequest imageRequest) {

		StringBuilder sb = new StringBuilder();

		switch (imageRequest.getQuality().getQualityEnum()) {
		case bitonal:
			sb.append("-monochrome ");
			break;
		case gray:
			sb.append("-colorspace ").append("Gray ");
			break;
		case color:
		case native_default:
		default:
			break;
		}

		return sb;
	}

	private ExtensionEnum manageFormat(ImageRequest imageRequest) {

		ExtensionEnum extensionEnum = null;
		
		switch (imageRequest.getFormat().getFormat()) {
		case gif:
			extensionEnum = ExtensionEnum.gif;
			break;
		case jp2:
			extensionEnum=ExtensionEnum.jp2;
			break;
		case jpg:
			extensionEnum = ExtensionEnum.jpg;
			break;
		case pdf:
			extensionEnum= ExtensionEnum.pdf;
			break;
		case png:
			extensionEnum=ExtensionEnum.png;
			break;
		case tif:
			extensionEnum=ExtensionEnum.tif;
			break;
		default:
			break;
		}

		return extensionEnum;
	}
	
	private int percentOf(int digit, double percentage) {
		return (int) Math.floor((digit * percentage) / 100);
	}

}
