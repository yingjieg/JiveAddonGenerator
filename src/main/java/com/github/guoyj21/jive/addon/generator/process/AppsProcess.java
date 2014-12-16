package com.github.guoyj21.jive.addon.generator.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.github.guoyj21.jive.addon.generator.ProgramPathUtil;
import com.github.guoyj21.jive.addon.generator.configuration.Apps;
import com.github.guoyj21.jive.addon.generator.configuration.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AppsProcess implements Process {
	private List<Object> apps = new ArrayList<Object>();

	@Override
	public void process(Config config) {
		createAppWebDir(config);
		updateDefinitionTemplate(config);
	}

	private void updateDefinitionTemplate(Config config) {
		String appStylePath = ProgramPathUtil.getCurrentPath("styles") + "/app/definition.json";
		String definitionPath = ProgramPathUtil.getCurrentPath("jive-addon")
				+ "/src/main/templates/definition.template";
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		for (Apps app : config.getApps()) {
			try {
				String content = FileUtils.readFileToString(new File(appStylePath));
				this.apps.add(gson.fromJson(content.replace("[appName]", app.getName()), Object.class));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileUtils.writeStringToFile(new File(definitionPath), ", \nosapps:" + gson.toJson(this.apps), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createAppWebDir(Config config) {
		String addonPath = System.getProperty("addon.name.path");
		String appWebPath = ProgramPathUtil.getCurrentPath("jive-addon") + "/src/main/webapp/apps/";
		for (Apps app : config.getApps()) {
			new File(addonPath + "/apps/" + app.getName());

			try {
				FileUtils.copyDirectory(new File(ProgramPathUtil.getCurrentPath("styles") + "/app/public"), new File(
						appWebPath + "/" + app.getName()));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
