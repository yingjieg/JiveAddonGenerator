package com.github.guoyj21.jive.addon.generator;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.github.guoyj21.jive.addon.generator.configuration.Config;
import com.github.guoyj21.jive.addon.generator.configuration.annotations.AddonProcess;
import com.github.guoyj21.jive.addon.generator.process.Processor;

public class Generator {

	@Parameter(names = { "--help", "-help", "-?" }, description = "Get help for the particular command.")
	private Boolean help = false;

	@Parameter(names = "-config", required = false, description = "Config File Path")
	private String configFilePath = "";

	@Parameter(names = "-sdk", required = true, description = "Jive SDK Java on Jersey Path")
	private String sdkPath = "";

	private void usage() {
		System.out.println("This is usage for Jive Add-on Generator");
	}

	public static final String SEPERATOR = File.separator;

	public void run(String[] args) {
		new JCommander(this, args);

		if (this.help) {
			this.usage();
		}

		createTempDir();

		System.setProperty("jive.sdk.path", this.sdkPath);

		doProcess();
		String currentPath = this.getCurrentPath();

		createBaseProjectStructure();
	}

	private void createTempDir() {
		try {
			File tempDir = File.createTempFile("generator", "temp");
			if(tempDir.mkdir()) {
				System.out.println("++++++++++++++++++++");
			}
			System.out.println(tempDir.getPath());
			FileUtils.copyInputStreamToFile(Thread.currentThread().getContextClassLoader().getResourceAsStream("jive-addon"),
					tempDir);

			for (String f : tempDir.list()) {
				System.out.println(f + "---");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createBaseProjectStructure() {
		File sdkFolder = new File(sdkPath);
		System.out.println(sdkPath);

		// if (sdkFolder.exists()) {
		// copySourceSdk();
		// } else {
		// System.out.println("Error, stop");
		// }
	}

	private void doProcess() {
		ObjectMapper mapper = new ObjectMapper();
		InputStream configStream = ProgramPathUtil.getCurrentPath("config.json");
		Config config = null;
		try {
			config = mapper.readValue(configStream, Config.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Class c = config.getClass();
		Processor processor = new Processor();
		for (Field field : c.getDeclaredFields()) {
			AddonProcess anno = field.getAnnotation(AddonProcess.class);
			System.out.println(anno.name());
			processor.registerProcess(((AddonProcess) anno).name());
		}
		processor.process(config);
	}

	private void copySourceSdk() {
		try {
			File addon = new File(getCurrentPath() + "jive-addon");
			System.out.println(addon.setWritable(true) + "------------");
			FileUtils.copyDirectory(addon, new File(getCurrentPath() + "jive-sdk-java-jersey" + SEPERATOR
					+ "jive-addon"));

			FileUtils.copyDirectory(new File(this.sdkPath + SEPERATOR + "jive-sdk"), new File(getCurrentPath()
					+ "jive-sdk-java-jersey" + SEPERATOR + "jive-sdk"));

			FileUtils.copyFile(new File(this.sdkPath + SEPERATOR + "pom.xml"), new File(getCurrentPath()
					+ "jive-sdk-java-jersey" + SEPERATOR + "pom.xml"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getCurrentPath() {
		URL url = Thread.currentThread().getContextClassLoader().getResource("");
		System.out.println(url.getPath());
		return url.getPath();
	}

	public static void main(String[] args) {
		Generator generator = new Generator();
		generator.run(new String[] { "-config", "C:\\MSDE\\yingjieg\\git\\test", "-sdk",
				"C:\\MSDE\\yingjieg\\git\\test\\jive-sdk-java-jersey" });
	}

	public Boolean getHelp() {
		return help;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public String getSdkPath() {
		return sdkPath;
	}
}
