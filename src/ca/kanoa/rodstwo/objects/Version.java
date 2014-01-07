package ca.kanoa.rodstwo.objects;

public class Version {

	private int major;
	private int minor;
	private int build;

	public Version(int major, int minor, int build) {
		this.major = major;
		this.minor = minor;
		this.build = build;
	}

	public static Version parseString(String str) {
		int major = 1, minor = 00, build = 0000;
		String[] raw = str.split("\\.");
		try {
			major = Integer.parseInt(raw[0]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
		try {
			minor = Integer.parseInt(raw[1]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
		try {
			build = Integer.parseInt(raw[2]);
		} catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {}
		return new Version(major, minor, build);
	}
	
	/**
	 * Compares two different versions to see which is newer
	 * @param v1 The first version to compare
	 * @param v2 The second version to compare
	 * @return 1 if v2 is newer than v1, 0 if their the same, and -1 if v1 is newer than v2
	 */
	public static int compare(Version v1, Version v2) {
		if (v1.getMajorVersion() > v2.getMajorVersion())
			return -1;
		else if (v2.getMajorVersion() > v1.getMajorVersion())
			return 1;
		else if (v1.getMinorVersion() > v2.getMinorVersion())
			return -1;
		else if (v2.getMinorVersion() > v1.getMinorVersion())
			return 1;
		else if (v1.getBuildVersion() > v2.getBuildVersion())
			return -1;
		else if (v2.getBuildVersion() > v1.getBuildVersion())
			return 1;
		else
			return 0;
	}

	public String getVersion() {
		return String.format("%d.%02d.%04d", major, minor, build);
	}
	
	@Override
	public String toString() {
		return getVersion();
	}

	public int getMajorVersion() {
		return major;
	}

	public int getMinorVersion() {
		return minor;
	}

	public int getBuildVersion() {
		return build;
	}

}
