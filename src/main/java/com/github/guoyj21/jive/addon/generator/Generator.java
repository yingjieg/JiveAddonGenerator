package com.github.guoyj21.jive.addon.generator;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Generator {

	@Parameter(names = { "--help", "-help", "-?", "-h" }, description = "Get help for the particular command.")
	private Boolean help;

	@Parameter(names = "-config", required = true, description = "Config File Path")
	private String configFilePath;

	private void usage() {
		System.out.println("This is usage for Jive Add-on Generator");
	}

	public void run(String[] args) {
		new JCommander(this, args);

		if (this.help) {
			this.usage();
		}
	}

	private void createBaseProjectStructure() {

	}

	public static void main(String[] args) {
		System.out.println("xx");

		Generator jct = new Generator();

		jct.run(new String[] { "-help" });

	}

	public Boolean getHelp() {
		return help;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}
}
