package com.github.guoyj21.jive.addon.generator.process;

import java.util.ArrayList;
import java.util.List;

import com.github.guoyj21.jive.addon.generator.process.handler.Handler;

public class Processor {
	private List<Handler> handlers = new ArrayList<>();

	public void registerHandler(Handler handler) {
		this.handlers.add(handler);
	}

	public void clearHandlers() {
		this.handlers.clear();
	}

	public void process() {
		for (Handler h : this.handlers) {
			h.process();
		}
	}
}
