package com.github.guoyj21.jive.addon.generator;

import java.io.InputStream;

public class ProgramPathUtil {
	
	public static InputStream getCurrentPath(String fileName) {
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
	}
	
	public static String getSdkPath() {
		return System.getProperty("jive.sdk.path");
	}
}
