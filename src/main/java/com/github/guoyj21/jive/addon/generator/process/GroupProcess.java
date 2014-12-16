package com.github.guoyj21.jive.addon.generator.process;

import java.io.File;

import com.github.guoyj21.jive.addon.generator.ProgramPathUtil;
import com.github.guoyj21.jive.addon.generator.configuration.Config;

public class GroupProcess implements Process {

	@Override
	public void process(Config config) {
		String workPath = ProgramPathUtil.getCurrentPath("jive-addon") + "/src/main/java/"
				+ config.getGroup().replace(".", File.separator);
		if (new File(workPath).mkdirs()) {
			System.setProperty("addon.group.path", workPath);
		} else {
			System.setProperty("addon.group.path", workPath);
			System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
		}
	}
}
