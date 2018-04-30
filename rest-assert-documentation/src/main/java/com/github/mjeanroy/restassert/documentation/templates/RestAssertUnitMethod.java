package com.github.mjeanroy.restassert.documentation.templates;

import java.util.List;

import com.github.mjeanroy.restassert.documentation.Method;
import com.github.mjeanroy.restassert.documentation.javadoc.JavadocParam;

class RestAssertUnitMethod implements TemplateModel {
	private final Method method;

	RestAssertUnitMethod(Method method) {
		this.method = method;
	}

	@Override
	public String getName() {
		return method.getName();
	}

	@Override
	public String getDescription() {
		return method.getJavadoc().getDescription();
	}

	@Override
	public List<JavadocParam> getArguments() {
		List<JavadocParam> arguments = method.getArguments();
		return arguments.subList(1, arguments.size());
	}

	@Override
	public boolean hasArguments() {
		return method.getArguments().size() > 1;
	}
}
