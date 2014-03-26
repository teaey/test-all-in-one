

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.UUID;
import java.util.prefs.Preferences;

public class LicenseGenerator {

	static String url = "http://127.0.0.1:8888";

	static int version = 5;

	static long prolongationPeriod = 30 * 60 * 1000;

	static UUID IDEA = UUID.fromString("49c202d4-ac56-452b-bb84-735056242fb3");

	static String ticketId = "ticketId";

	static String machineId;

	static String licensee = "temp.user";//System.getProperty("user.name");
	//
	static String permanentLicense = "JetBrains." + IDEA + ".PermanentLicense";

	static String keyPath = System.getProperty("user.home") + File.separator
			+ ".IntelliJIdea13" + File.separator + "config" + File.separator
			+ "idea13.key";

	private static String getSignature(String url, String machineId,
			long timestamp, long period, String licensee, String ticketId,
			String licenseType) {
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		sb.append(period);
		if (timestamp != 0L) {
			sb.append(timestamp);
		}
		sb.append(licensee);
		sb.append(machineId);
		sb.append(ticketId);
		sb.append(licenseType);

		int i1 = sb.toString().hashCode();
		return Integer.toHexString(i1);
	}

	private static void writeURL() throws IOException {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(keyPath);
			fos.write(255);
			fos.write(255);
			String txt = "URL:" + url;
			for (int i = 0; i < txt.length(); i++) {
				int j = txt.charAt(i);
				int k = (byte) (j & 0xFF);
				int m = (byte) (j >> 8 & 0xFF);
				fos.write(k);
				fos.write(m);
			}
			fos.flush();
		} finally {
			if (fos != null)
				fos.close();
		}
	}

	private static void clean() throws IOException {
		File file = new File(keyPath);
		if (file.exists()) {
			file.delete();
		} else {
			file.getParentFile().mkdirs();
		}
		//
		Preferences.userRoot().remove(permanentLicense);

	}

	public static void main(String[] args) throws Exception {
		clean();
		//
		writeURL();
		//
		String str = getSignature(url, machineId, 0L, prolongationPeriod,
				licensee, ticketId, String.valueOf(version));

		Properties props = new Properties();
		props.setProperty("server.url", url);
		props.setProperty("prolongation.period",
				String.valueOf(prolongationPeriod));
		props.setProperty("machine.id", machineId);
		props.setProperty("ticket.id", ticketId);
		props.setProperty("licensee", licensee);
		props.setProperty("signature", str);
		props.setProperty("licenseType", String.valueOf(version));

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			props.store(bos,
					"License server ticket information. Please do not alter this data");
		} catch (IOException e) {

		} finally {
			try {
				bos.close();
				Preferences.userRoot().putByteArray(permanentLicense,
						bos.toByteArray());
			} catch (IOException e) {

			}
		}
	}

	static {
		machineId = Preferences.userRoot().get("JetBrains.UserIdOnMachine",
				null);
		if ((machineId == null) || (machineId.length() == 0)) {
			machineId = UUID.randomUUID().toString();
			Preferences.userRoot().put("JetBrains.UserIdOnMachine", machineId);
		}
	}

}
