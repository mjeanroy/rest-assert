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

import com.github.mjeanroy.restassert.documentation.javadoc.JavaDoc;
import com.github.mjeanroy.restassert.documentation.javadoc.JavaDocParser;
import com.github.mjeanroy.restassert.documentation.templates.TemplateProcessor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * The documentation processor that will extract method from classes
 * annotated with {@link Documentation} and will generate markdown documentation
 * from it.
 */
public class DocumentationProcessor extends AbstractProcessor {

	/**
	 * The messager API, used to display error message.
	 */
	private Messager messager;

	/**
	 * The directory where documentation will be written.
	 */
	private String output;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {
		super.init(processingEnvironment);
		messager = processingEnvironment.getMessager();
		output = processingEnvironment.getOptions().get("output");
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		for (Element element : roundEnv.getElementsAnnotatedWith(Documentation.class)) {
			if (element.getKind() != ElementKind.CLASS) {
				messager.printMessage(Diagnostic.Kind.ERROR, "Can be applied only to classes.");
				return true;
			}

			processClass((TypeElement) element);
		}

		return true;
	}

	/**
	 * Process class: extract all public and non static methods, sort them by name and
	 * parse associated JavaDoc to produce documentation as a markdown file.
	 *
	 * @param element Class element.
	 */
	private void processClass(TypeElement element) {
		List<? extends Element> elements = element.getEnclosedElements();
		List<DocumentedMethod> methods = new ArrayList<>(elements.size());
		for (Element enclosedElement : elements) {
			if (enclosedElement.getKind() == ElementKind.METHOD && shouldProcessMethod(enclosedElement)) {
				methods.add(processMethod(enclosedElement));
			}
		}

		Collections.sort(methods, new Comparator<DocumentedMethod>() {
			@Override
			public int compare(DocumentedMethod m1, DocumentedMethod m2) {
				return m1.getName().compareTo(m2.getName());
			}
		});

		generateDocumentation(methods);
	}

	/**
	 * Check if method should be processed: only public and non static method
	 * are kept, other methods should be ignored.
	 *
	 * @param method The method.
	 * @return {@code true} if method should be processed, {@code false} otherwise.
	 */
	private boolean shouldProcessMethod(Element method) {
		Set<Modifier> modifiers = method.getModifiers();
		return modifiers.contains(Modifier.PUBLIC) && !modifiers.contains(Modifier.STATIC);
	}

	/**
	 * Parse method JavaDoc and instantiate a {@link DocumentedMethod} from it.
	 *
	 * @param element The method.
	 * @return The documented method with extract javadoc.
	 */
	private DocumentedMethod processMethod(Element element) {
		String docComment = processingEnv.getElementUtils().getDocComment(element);
		JavaDoc javadoc = JavaDocParser.parse(docComment);
		return new DocumentedMethod(element.getSimpleName().toString(), javadoc);
	}

	/**
	 * Generate markdown documentation from documented methods and write
	 * output to configured directory ({@link #output}).
	 *
	 * @param methods Method to include in documentation.
	 */
	private void generateDocumentation(List<DocumentedMethod> methods) {
		for (TemplateProcessor processor : TemplateProcessor.values()) {
			String output = processor.process(methods);
			writeDocumentation(output);
		}
	}

	/**
	 * Write documentation output.
	 *
	 * @param documentation The documentation output.
	 */
	private void writeDocumentation(String documentation) {
		try {
			Files.write(Paths.get(output, "doc.md"), documentation.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			messager.printMessage(Diagnostic.Kind.ERROR, ex.getMessage());
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return singleton(Documentation.class.getCanonicalName());
	}
}
