package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class JavaFieldTest extends TestEnvironment {
    @Test
    public void testAllInnerFields() throws Exception {
        testAllInnerFields("/JavaClasses/SimpleClass.java",
                "f1", "f1");

        testAllInnerFields("/JavaClasses/AbstractClass.java",
                "f1");

        testAllInnerFields("/JavaClasses/ClassWithInnerClass.java",
                "f1", "f1");

        testAllInnerFields("/JavaClasses/ClassWithLocalClass.java",
                "f1", "f1");

        testAllInnerFields("/JavaClasses/Interface.java");
    }

    // TODO: this method is very similar to testAllInnerMethods
    // TODO: create matcher
    private void testAllInnerFields(final @NotNull String fileName,
                                    final @NotNull String... expectedFields) throws Exception {
        File file = addToProjectDir(fileName);
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(new DullJavaEntitiesHolder(unit, typeSolver)
                        .allInnerEntities(JavaField.creator)
                        .stream()
                        .map(JavaField::simpleName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(expectedFields));
    }
}