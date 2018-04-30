package com.github.mjeanroy.restassert.documentation;

import java.util.List;
import java.util.Objects;

import com.github.mjeanroy.restassert.documentation.javadoc.Javadoc;
import com.github.mjeanroy.restassert.documentation.javadoc.JavadocParam;

public class Method {
	private final String name;
	private final Javadoc javadoc;

	public Method(String name, Javadoc javadoc) {
		this.name = name;
		this.javadoc = javadoc;
	}

	public String getName() {
		return name;
	}

	public Javadoc getJavadoc() {
		return javadoc;
	}

	public List<JavadocParam> getArguments() {
		return javadoc.getParams();
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}

		if (o instanceof Method) {
			Method m = (Method) o;
			return Objects.equals(name, m.name) && Objects.equals(javadoc, m.javadoc);
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, javadoc);
	}
}
