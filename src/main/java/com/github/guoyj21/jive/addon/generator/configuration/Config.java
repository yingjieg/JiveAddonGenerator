package com.github.guoyj21.jive.addon.generator.configuration;

import java.util.List;
import java.util.Map;

import com.github.guoyj21.jive.addon.generator.configuration.annotations.AddonProcess;

public class Config {
	@AddonProcess(name = "GroupProcess", description = "xx")
	private String group;

	@AddonProcess(name = "ArtifactIdProcess", description = "xxx")
	private String artifactId;

	@AddonProcess(name = "AddonNameProcess", description = "xxxx")
	private String addonName;

	@AddonProcess(name = "TilesProcess", description = "xxxx")
	private Map<String, String> tiles;

	@AddonProcess(name = "AppsProcess", description = "xxxxx") 
	private List<String> apps;

	@AddonProcess(name = "StorageProcess", description = "xxxxxxx")
	private String storage;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getAddonName() {
		return addonName;
	}

	public void setAddonName(String addonName) {
		this.addonName = addonName;
	}

	public Map<String, String> getTiles() {
		return tiles;
	}

	public void setTiles(Map<String, String> tiles) {
		this.tiles = tiles;
	}

	public List<String> getApps() {
		return apps;
	}

	public void setApps(List<String> apps) {
		this.apps = apps;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

}
