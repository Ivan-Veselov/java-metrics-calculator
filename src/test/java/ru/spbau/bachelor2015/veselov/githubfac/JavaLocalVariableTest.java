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
    public void testAllInnerLocalVariablesOf() throws Exception {
        testAllInnerLocalVariablesOf("/JavaClasses/SimpleClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariablesOf("/JavaClasses/AbstractClass.java",
                "l1", "l2", "l3");

        testAllInnerLocalVariablesOf("/JavaClasses/ClassWithInnerClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariablesOf("/JavaClasses/ClassWithLocalClass.java",
                "l1", "l2", "l3", "l1", "l2", "l3");

        testAllInnerLocalVariablesOf("/JavaClasses/Interface.java",
                "l1", "l2", "l3");
    }

    // TODO: this method is very similar to testAllInnerMethodsOf
    // TODO: create matcher
    private void testAllInnerLocalVariablesOf(
                              final @NotNull String fileName,
                              final @NotNull String... expectedLocalVariables) throws Exception {
        File file = addSourceFileToProjectDir(fileName);
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance()
                        .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                            JavaLocalVariable.creator)
                        .stream()
                        .map(JavaLocalVariable::simpleName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(expectedLocalVariables));
    }
}