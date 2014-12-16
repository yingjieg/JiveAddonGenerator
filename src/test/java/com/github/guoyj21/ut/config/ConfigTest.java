package com.github.guoyj21.ut.config;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import com.github.guoyj21.jive.addon.generator.configuration.Config;

public class ConfigTest {
	@Test
	public void configTest() {
		ObjectMapper mapper = new ObjectMapper();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.json").getPath();
		System.out.println(path);
		try {
			Config config = mapper.readValue(new File(path), Config.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
