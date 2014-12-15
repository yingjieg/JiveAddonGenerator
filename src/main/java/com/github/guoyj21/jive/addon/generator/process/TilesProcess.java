package com.github.guoyj21.jive.addon.generator.process;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;

import com.github.guoyj21.jive.addon.generator.configuration.Config;

public class TilesProcess implements Process {

	private static final String TILE_BACKEND_PATH = "C:/MSDE/yingjieg/git/test/jive-sdk-java-jersey/jive-addon/src/main/java/com/jivesoftware/addon/example/tile/";

	@Override
	public void process(Config config) {
		Map<String, String> tiles = config.getTiles();

		Iterator entries = tiles.entrySet().iterator();

		while (entries.hasNext()) {
			Entry<String, String> entry = (Entry<String, String>) entries.next();
			System.out.println(entry.getKey());
			System.out.println(entry.getValue());
			copyTileBackend(entry.getKey(), entry.getValue());
			copyTileWeb(entry.getKey(), entry.getValue());
			updateDenfinitionTemplate(entry.getKey(), entry.getValue());
			updateAddonPom(entry.getKey(), entry.getValue());
		}

		// update pom.xml
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
			String content = FileUtils.readFileToString(new File(tilePath));
			String newContent = content.replace(target, tileName);
			String dest = Thread.currentThread().getContextClassLoader().getResource("")
					+ "/jive-sdk-java-jersey/jive-addon/src/main/java/com/jivesoftware/addon/example/tile/";
			File file = new File(dest);
			file.mkdirs();
			System.out.println(dest);
			FileUtils.writeStringToFile(new File(dest + "/" + tileName + ".java"), newContent);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void copyTileWeb(String tileType, String tileName) {

	}

	private void updateDenfinitionTemplate(String tileType, String tileName) {

	}

	private void updateAddonPom(String tileType, String tileName) {

	}
}
