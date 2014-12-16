package com.github.guoyj21.jive.addon.generator.process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.github.guoyj21.jive.addon.generator.ProgramPathUtil;
import com.github.guoyj21.jive.addon.generator.configuration.Config;
import com.github.guoyj21.jive.addon.generator.configuration.Tiles;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TilesProcess implements Process {

	private static final String TILE_BACKEND_PATH = "jive-addon/src/main/java/com/jivesoftware/addon/example/tile/";
	private String backendWorkPath = null;
	private String webWorkPath = null;
	private List<Object> tiles = new ArrayList<Object>();

	@Override
	public void process(Config config) {
		this.backendWorkPath = generateTileBackendDir(config.getGroup(), config.getAddonName());
		this.webWorkPath = generateTileWebDir();

		for (Tiles tile : config.getTiles()) {
			copyTileBackend(tile.getStyle(), tile.getName());
			copyTileWeb(tile.getStyle(), tile.getName());
			readTileJsonTemplate(tile.getStyle(), tile.getName());
		}
		updateDefinitiontemplate();

		// update pom.xml
	}

	private String generateTileBackendDir(String packagePath, String addonName) {
		String addonPath = System.getProperty("addon.name.path");
		System.out.println(addonPath + "---------------");
		File file = new File(addonPath + "/tile/");
		file.mkdirs();
		return file.getPath();
	}

	private String generateTileWebDir() {
		String path = ProgramPathUtil.getCurrentPath("jive-addon") + "/src/main/webapp/tiles/";
		System.out.println(path + "++++++++++++++");
		if (new File(path).mkdirs()) {
			return path;
		} else {
			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzz");
			return "";
		}
	}

	private void copyTileBackend(String tileType, String tileName) {
		String tilePath = "";
		String target = "";
		if (tileType.equals("tile-list")) {
			tilePath = TILE_BACKEND_PATH + "MyExampleListTile.java";
			target = "MyExampleListTile";
		} else if (tileType.equals("tile-table")) {
			tilePath = TILE_BACKEND_PATH + "MyExampleTableTile.java";
			target = "MyExampleListTile";
		}

		try {
			String content = FileUtils.readFileToString(new File(ProgramPathUtil.getSdkPath() + File.separator
					+ tilePath));
			String newContent = content.replace(target, tileName);
			FileUtils.writeStringToFile(new File(this.backendWorkPath + File.separator + tileName + ".java"),
					newContent);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void copyTileWeb(String tileType, String tileName) {
		String sdkPath = System.getProperty("jive.sdk.path");
		String tilePath = "";
		String target = "";
		if (tileType.equals("tile-list")) {
			tilePath = sdkPath + "/jive-addon/src/main/webapp/tiles/" + "jersey-example-list/";
			target = "MyExampleListTile";
		} else if (tileType.equals("tile-table")) {
			tilePath = sdkPath + "/jive-addon/src/main/webapp/tiles/" + "jersey-example-table/";
			target = "MyExampleListTile";
		}

		try {
			FileUtils.copyDirectory(new File(tilePath), new File(this.webWorkPath + "/" + tileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void readTileJsonTemplate(String tileType, String tileName) {
		String tileJsonPath = ProgramPathUtil.getCurrentPath() + "/styles/" + tileType + "/definition.json";
		try {
			String content = FileUtils.readFileToString(new File(tileJsonPath));

			ObjectMapper mapper = new ObjectMapper();
			Object obj = mapper.readValue(content.replace("[tileName]", tileName), Object.class);
			this.tiles.add(obj);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void updateDefinitiontemplate() {
		String definitionPath = ProgramPathUtil.getCurrentPath() + "/jive-addon/src/main/templates/definition.template";

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File definitionJson = new File(definitionPath);
		try {
			FileUtils.writeStringToFile(definitionJson, "\"tiles\": " + gson.toJson(this.tiles), true);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
