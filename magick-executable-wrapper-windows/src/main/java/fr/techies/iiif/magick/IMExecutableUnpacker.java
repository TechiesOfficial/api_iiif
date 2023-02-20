package fr.techies.iiif.magick;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IMExecutableUnpacker {

	private Logger logger = LogManager.getLogger(IMExecutableUnpacker.class);

	private File IMMagickExecutablePath;

	public IMExecutableUnpacker(File unpackedTargertPath) {

		FileOutputStream fileOutputStream = null;

		try {
			fileOutputStream = new FileOutputStream(new File(unpackedTargertPath, "magick.exe"));
			this.getClass().getClassLoader().getResourceAsStream("magick/win/magick.exe").transferTo(fileOutputStream);

			fileOutputStream.flush();
			this.IMMagickExecutablePath = new File(unpackedTargertPath, "magick.exe").getAbsoluteFile();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public File getMagickExecutable() throws IOException {
		return this.IMMagickExecutablePath;
	}

}
