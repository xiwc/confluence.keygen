package confluence.keygen.linux;

public class MainApp {

	public static void main(String[] args) {

		genLicense();

		if (args != null && args.length > 0) {
			patchApp(args[0]);
		}

	}

	public static void patchApp(String jarFilePath) {

		// String jarFilePath =
		// "C:\\Users\\xi\\Desktop\\confluence_keygen\\atlassian-extras-2.4.jar";

		Patch patch = new Patch();
		patch.patchFile(jarFilePath);

		System.out.println("Jar successfully patched.");
	}

	public static void genLicense() {

		String name = "xiweicheng";
		String email = "invisible@scene.nl";
		String org = "iNViSiBLE TEAM";
		String sid = "	BMX2-WRP8-E8DL-EOOW";

		LicenseFile key = new LicenseFile();
		System.out.println(key.genLicense(name, email, org, sid));

		System.out.println("Key successfully generated.");
	}
}
