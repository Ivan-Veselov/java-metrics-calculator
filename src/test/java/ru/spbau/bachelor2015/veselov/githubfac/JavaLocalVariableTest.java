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

public class JavaLocalVariableTest extends TestEnvironment {
    @Test
    public void testAllInnerLocalVariables() throws Exception {
        testAllInnerLocalVariables("/JavaClasses/SimpleClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariables("/JavaClasses/AbstractClass.java",
                "l1", "l2", "l3");

        testAllInnerLocalVariables("/JavaClasses/ClassWithInnerClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariables("/JavaClasses/ClassWithLocalClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariables("/JavaClasses/Interface.java",
                "l1", "l2", "l3");
    }

    // TODO: this method is very similar to testAllInnerMethods
    // TODO: create matcher
    private void testAllInnerLocalVariables(
                              final @NotNull String fileName,
                              final @NotNull String... expectedLocalVariables) throws Exception {
        File file = addToProjectDir(fileName);
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(new DullJavaEntitiesHolder(unit, typeSolver)
                        .allInnerEntities(JavaLocalVariable.creator)
                        .stream()
                        .map(JavaLocalVariable::simpleName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(expectedLocalVariables));
    }
}