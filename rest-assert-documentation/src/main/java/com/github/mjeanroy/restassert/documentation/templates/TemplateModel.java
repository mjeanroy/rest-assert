package com.github.mjeanroy.restassert.documentation.templates;

import java.util.List;

import com.github.mjeanroy.restassert.documentation.javadoc.JavadocParam;

public interface TemplateModel {

	String getName();

	String getDescription();

	List<JavadocParam> getArguments();

	boolean hasArguments();
}
