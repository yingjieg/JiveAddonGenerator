package com.github.guoyj21.ut.process;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.guoyj21.jive.addon.generator.configuration.Config;
import com.github.guoyj21.jive.addon.generator.configuration.annotations.AddonProcess;

public class ProcessTest {

	@Test
	public void processTest() {
		ObjectMapper mapper = new ObjectMapper();
		String path = Thread.currentThread().getContextClassLoader().getResource("config.json").getPath();
		System.out.println(path);
		Config config = null;
		try {
			config = mapper.readValue(new File(path), Config.class);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Class c = config.getClass();

		for (Field field : c.getDeclaredFields()) {
			System.err.println(field.getName());
			AddonProcess anno = field.getAnnotation(AddonProcess.class);
			System.out.println(anno.description());
		}
	}
}
