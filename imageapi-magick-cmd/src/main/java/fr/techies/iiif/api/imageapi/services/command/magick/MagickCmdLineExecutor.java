package fr.techies.iiif.api.imageapi.services.command.magick;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.techies.iiif.api.imageapi.imagerequest.model.ImageRequest;
import fr.techies.iiif.lib.cmd.GenericCommandLineExecutor;
import fr.techies.iiif.lib.utils.ImageFileUtil;
import fr.techies.iiif.lib.utils.enums.ExtensionEnum;
import fr.techies.iiif.magick.IMExecutableUnpacker;
import fr.techies.iiif.services.os.OSEnum;

@Service
public class MagickCmdLineExecutor {

	@Value("iiif.dir.path")
	private File unpackedTargertPath;
	
	@Autowired
	private GenericCommandLineExecutor commandLineExecutor;

	@Autowired
	private IdentifyCmdLineExecutor identifyCmdLineExecutor;

	private IMExecutableUnpacker imExecutableUnpacker;
	
	public MagickCmdLineExecutor() {

		this.imExecutableUnpacker = new IMExecutableUnpacker(OSEnum.Windows, this.unpackedTargertPath);
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
		sb.append(this.manageSize(imageRequest));
		sb.append(" ");
		sb.append(this.manageRotation(imageRequest));
		sb.append(" ");
		sb.append(this.manageQuality(imageRequest));
		
		ExtensionEnum extensionEnum = this.manageFormat(imageRequest);
		
		sb.append(" ");
		sb.append(outFileName);

		this.commandLineExecutor.exec(sb.toString());
		
		return ImageFileUtil.getImageAsBytes(outFileName.toString(),
				extensionEnum);
	}

	private ExtensionEnum manageFormat(ImageRequest imageRequest) {

		StringBuilder sb = new StringBuilder();
		ExtensionEnum extensionEnum = null;
		
		switch (imageRequest.getFormat().getFormat()) {
		case gif:
			extensionEnum = ExtensionEnum.gif;
			break;
		case jp2:
			extensionEnum=ExtensionEnum.jp2;
		case jpg:
			extensionEnum = ExtensionEnum.jpg;
		case pdf:
			extensionEnum= ExtensionEnum.pdf;
		case png:
			extensionEnum=ExtensionEnum.png;
		case tif:
			extensionEnum=ExtensionEnum.tif;
		default:
			break;
		}

		return extensionEnum;
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

	private StringBuilder manageRotation(ImageRequest imageRequest) {

		StringBuilder sb = new StringBuilder();
		int degree = -1;

		if (imageRequest.getRotation().isMirroring()) {
			sb.append("-flop ");
		}

		// A priori c'est comme cela que l'on fait de la transparence mais ca met un
		// fond noir!
		// Testé sur chrome et ie
		if (imageRequest.getRotation().getDegree() != 0)
			sb.append("-background 'rgba(0,0,0,0)' -rotate " + degree + " +repage");

		return sb;
	}

	private StringBuilder manageSize(ImageRequest imageRequest) {

		StringBuilder sb = new StringBuilder();

		return sb;
	}

	private StringBuilder manageRegion(ImageRequest imageRequest, IdentifyResultBean identifyResultBean) {

		StringBuilder sb = new StringBuilder();

		switch (imageRequest.getRegion().getRegionEnum()) {
		case full:
			// Rien à faire
			break;
		case square:
			// On récupére les dimensions de l'image via identify.exe et on applique
			break;
		case pixel:
			sb.append("-extract " + imageRequest.getRegion().getRegionPixel().getPixelW() + "x"
					+ imageRequest.getRegion().getRegionPixel().getPixelH() + "+"
					+ imageRequest.getRegion().getRegionPixel().getPixelX() + "+"
					+ imageRequest.getRegion().getRegionPixel().getPixelY());
			break;
		case pct:
			sb.append("-extract " + imageRequest.getRegion().getRegionPCT().getPctW() + "x"
					+ imageRequest.getRegion().getRegionPCT().getPctH() + "+"
					+ imageRequest.getRegion().getRegionPCT().getPctX() + "+"
					+ imageRequest.getRegion().getRegionPCT().getPctY());
			break;
		}

		return sb;
	}

}
