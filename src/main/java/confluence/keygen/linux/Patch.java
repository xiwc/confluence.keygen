package confluence.keygen.linux;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public final class Patch {

	// private final int BUFFER = 4096;

	public final boolean patchFile(String jarFilePath) {
		try {
			File jarFile = new File(jarFilePath);
			File temp = File.createTempFile("patch", null);

			ZipInputStream zis = new ZipInputStream(
					new FileInputStream(jarFile));
			ZipOutputStream zos = new ZipOutputStream(
					new FileOutputStream(temp));

			zos.setLevel(9);
			zos.setMethod(8);

			ZipEntry next = null;
			while ((next = zis.getNextEntry()) != null) {
				ZipEntry current = new ZipEntry(next.getName());
				current.setComment(next.getComment());
				current.setTime(next.getTime());
				current.setExtra(next.getExtra());

				zos.putNextEntry(current);
				if (next.getName()
						.equals("com/atlassian/extras/decoder/v2/Version2LicenseDecoder.class")) {
					writeToStream(
							getClass()
									.getResourceAsStream(
									"/Version2LicenseDecoder.class"),
							zos);
				} else {
					writeToStream(zis, zos);
				}
				zis.closeEntry();
				zos.closeEntry();
			}
			zis.close();
			zos.flush();
			zos.close();

			temp.setLastModified(jarFile.lastModified());
			if (!jarFile.renameTo(new File(jarFile.getPath().substring(0,
					jarFile.getPath().lastIndexOf('.') + 1)
					+ "bak"))) {
				return false;
			}
			if (!temp.renameTo(jarFile)) {
				return false;
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
			return false;
		} catch (IOException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	private final void writeToStream(InputStream is, OutputStream os) {
		try {
			byte[] buffer = new byte['?'];
			int currentByte;
			while ((currentByte = is.read(buffer)) != -1) {
				os.write(buffer, 0, currentByte);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
