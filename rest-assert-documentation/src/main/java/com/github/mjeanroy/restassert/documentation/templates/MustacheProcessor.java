package com.github.mjeanroy.restassert.documentation.templates;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import com.samskivert.mustache.Mustache;

class MustacheProcessor {
	private static final MustacheProcessor INSTANCE = new MustacheProcessor();

	static MustacheProcessor getInstance() {
		return INSTANCE;
	}

	private final Mustache.Compiler mustache;

	private MustacheProcessor() {
		this.mustache = Mustache.compiler();
	}

	public String render(InputStream template, Map<String, Object> model) {
		Reader source = new InputStreamReader(template);
		return mustache.compile(source).execute(model);
	}
}
