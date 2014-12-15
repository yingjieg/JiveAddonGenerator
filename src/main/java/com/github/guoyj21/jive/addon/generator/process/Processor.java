package com.github.guoyj21.jive.addon.generator.process;

import java.util.ArrayList;
import java.util.List;

import com.github.guoyj21.jive.addon.generator.configuration.Config;

public class Processor {
	private List<Process> procs = new ArrayList<>();

	public void registerProcess(String processName) {
		Class clazz = null;
		try {
			clazz = Class.forName("com.github.guoyj21.jive.addon.generator.process." + processName);
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			this.procs.add((Process) clazz.newInstance());
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public void clearProcess() {
		this.procs.clear();
	}

	public void process(Config config) {
		for (Process p : this.procs) {
			p.process(config);
		}
	}
}
