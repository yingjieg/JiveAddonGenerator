package com.github.guoyj21.jive.addon.generator.process;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.github.guoyj21.jive.addon.generator.ProgramPathUtil;
import com.github.guoyj21.jive.addon.generator.configuration.Config;

public class AddonNameProcess implements Process {

	@Override
	public void process(Config config) {
		String workPath = System.getProperty("addon.group.path") + File.separator + config.getAddonName()
				+ File.separator;
		if (new File(workPath).mkdirs()) {
			System.setProperty("addon.name.path", workPath);
		} else {
			System.setProperty("addon.name.path", workPath);
			System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		}

		updateAddonPom(config);

		String addonPath = ProgramPathUtil.getCurrentPath("MyExampleAddon.java");
		File addonFile = new File(addonPath);
		String content = null;
		try {
			content = FileUtils.readFileToString(addonFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String newContent = StringUtils.replaceEach(content, new String[] { "${MyExampleAddon}", "${addonName}" },
				new String[] { "GoGoAddon", config.getAddonName() });

		try {
			FileUtils.writeStringToFile(new File(ProgramPathUtil.getCurrentPath("jive-addon") + "/src/main/java/"
					+ config.getGroup().replace(".", File.separator) + "/GoGoAddon.java"), newContent);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void updateAddonPom(Config config) {
		String pomPath = ProgramPathUtil.getCurrentPath("jive-addon") + "/pom.xml";
		String pomPathContent = null;
		try {
			pomPathContent = FileUtils.readFileToString(new File(pomPath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuilder tileConfig = new StringBuilder();
		tileConfig.append("<jive.addon.tile.list.displayName>xxxxxx</jive.addon.tile.list.displayName>\n");
		tileConfig.append("<jive.addon.tile.list.displayName></jive.addon.tile.list.displayName>\n");
		tileConfig.append("<jive.addon.tile.table.displayName>yyyyyyy</jive.addon.tile.table.displayName>\n");
		tileConfig.append("<jive.addon.tile.table.description></jive.addon.tile.table.description>\n");

		StringBuilder uuid = new StringBuilder();
		uuid.append("project.setProperty( \"jive.random.UUID2\" , UUID.randomUUID().toString() );\n");
		uuid.append("project.setProperty( \"jive.random.UUID3\" , UUID.randomUUID().toString() );\n");

		StringBuilder definition = new StringBuilder();
		definition
				.append("<replace file=\"src/main/extension/definition.json\" token=\"[[UUID2]]\" value=\"${jive.random.UUID2}\"/>\n");
		definition
				.append("<replace file=\"src/main/extension/definition.json\" token=\"[[UUID3]]\" value=\"${jive.random.UUID2}\"/>\n");

		String content = StringUtils.replaceEach(pomPathContent, new String[] { "[addonName]",
				"[addonTileConfiguration]", "[addonStorageConfiguration]", "[addonCartridgeConfigruation]", "[uuid]",
				"[definitionConfiguration] " }, new String[] { config.getAddonName(), tileConfig.toString(), "", "",
				uuid.toString(), definition.toString() });

		try {
			FileUtils.writeStringToFile(new File(pomPath), content);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
