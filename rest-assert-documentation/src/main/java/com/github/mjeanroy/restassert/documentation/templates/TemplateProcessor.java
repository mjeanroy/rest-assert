package com.github.mjeanroy.restassert.documentation.templates;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mjeanroy.restassert.documentation.Method;

public enum TemplateProcessor {

	REST_ASSERT_UNIT("rest-assert-unit.template.txt") {
		@Override
		public void process(List<Method> methods) {
			List<RestAssertUnitMethod> restAssertUnitMethods = new ArrayList<>(methods.size());
			for (Method method : methods) {
				restAssertUnitMethods.add(new RestAssertUnitMethod(method));
			}

			InputStream template = getClass().getResourceAsStream("/templates/" + this.template);
			Map<String, Object> model = new HashMap<>();
			model.put("methods", restAssertUnitMethods);

			String output = MustacheProcessor.getInstance().render(template, model);

			System.out.println(output);
		}
	};

	final String template;

	TemplateProcessor(String template) {
		this.template = template;
	}

	public abstract void process(List<Method> methods);

}
