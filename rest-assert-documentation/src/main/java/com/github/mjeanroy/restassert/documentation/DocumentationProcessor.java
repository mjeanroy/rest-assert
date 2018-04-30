/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2018 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.mjeanroy.restassert.documentation;

import static java.util.Collections.singleton;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.github.mjeanroy.restassert.documentation.javadoc.Javadoc;
import com.github.mjeanroy.restassert.documentation.javadoc.JavadocParser;
import com.github.mjeanroy.restassert.documentation.templates.TemplateProcessor;

public class DocumentationProcessor extends AbstractProcessor {

	private Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);

		messager = processingEnvironment.getMessager();
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(Documentation.class)) {
			if (element.getKind() != ElementKind.CLASS) {
				messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied to class.");
				return true;
			}

			processClass((TypeElement) element);
		}

		return true;
	}

	private void processClass(TypeElement element) {
		List<? extends Element> elements = element.getEnclosedElements();
		List<Method> methods = new ArrayList<>(elements.size());
		for (Element enclosedElement : elements) {
			if (enclosedElement.getKind() == ElementKind.METHOD && shouldProcessMethod(enclosedElement)) {
				methods.add(processMethod(enclosedElement));
			}
		}

		generateDocumentation(methods);
	}

	private boolean shouldProcessMethod(Element method) {
		Set<Modifier> modifiers = method.getModifiers();
		return modifiers.contains(Modifier.PUBLIC) && !modifiers.contains(Modifier.STATIC);
	}

	private Method processMethod(Element element) {
		String docComment = processingEnv.getElementUtils().getDocComment(element);
		Javadoc javadoc = JavadocParser.parse(docComment);
		return new Method(element.getSimpleName().toString(), javadoc);
	}

	private void generateDocumentation(List<Method> methods) {
		for (TemplateProcessor processor : TemplateProcessor.values()) {
			processor.process(methods);
		}
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return singleton(Documentation.class.getCanonicalName());
	}
}
