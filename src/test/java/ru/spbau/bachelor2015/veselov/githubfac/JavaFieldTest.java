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
    public void testAllInnerFieldsOf() throws Exception {
        testAllInnerFieldsOf("/JavaClasses/SimpleClass.java",
                "f1", "f1");

        testAllInnerFieldsOf("/JavaClasses/AbstractClass.java",
                "f1");

        testAllInnerFieldsOf("/JavaClasses/ClassWithInnerClass.java",
                "f1", "f1");

        testAllInnerFieldsOf("/JavaClasses/ClassWithLocalClass.java",
                "f1", "f1");

        testAllInnerFieldsOf("/JavaClasses/Interface.java");
    }

    // TODO: this method is very similar to testAllInnerMethodsOf
    // TODO: create matcher
    private void testAllInnerFieldsOf(final @NotNull String fileName,
                                      final @NotNull String... expectedFields) throws Exception {
        File file = addSourceFileToProjectDir(fileName);
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance()
                        .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                            JavaField.creator)
                        .stream()
                        .map(JavaField::simpleName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(expectedFields));
    }
}