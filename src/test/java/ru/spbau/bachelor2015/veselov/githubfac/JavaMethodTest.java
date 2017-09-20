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

public class JavaMethodTest extends TestEnvironment {
    @Test
    public void testAllInnerMethodsOf() throws Exception {
        testAllInnerMethodsOf("/JavaClasses/SimpleClass.java",
           "SimpleClass.m1",
                           "SimpleClass.m2",
                           "SimpleClass.m3",
                           "SimpleClass.mo",
                           "SimpleClass.mo",
                           "HiddenClass.m1",
                           "HiddenClass.m2",
                           "HiddenClass.m3",
                           "HiddenClass.mo",
                           "HiddenClass.mo");

        testAllInnerMethodsOf("/JavaClasses/AbstractClass.java",
                "AbstractClass.m1",
                                "AbstractClass.m2",
                                "AbstractClass.m3",
                                "AbstractClass.m4",
                                "AbstractClass.m5",
                                "AbstractClass.m6",
                                "AbstractClass.mo",
                                "AbstractClass.mo");

        testAllInnerMethodsOf("/JavaClasses/ClassWithInnerClass.java",
                "ClassWithInnerClass.m1",
                                "ClassWithInnerClass.m2",
                                "ClassWithInnerClass.m3",
                                "ClassWithInnerClass.mo",
                                "ClassWithInnerClass.mo",
                                "ClassWithInnerClass.InnerClass.m1",
                                "ClassWithInnerClass.InnerClass.m2",
                                "ClassWithInnerClass.InnerClass.m3",
                                "ClassWithInnerClass.InnerClass.mo",
                                "ClassWithInnerClass.InnerClass.mo");

        testAllInnerMethodsOf("/JavaClasses/ClassWithLocalClass.java",
                "ClassWithLocalClass.m0",
                                "ClassWithLocalClass.m1",
                                "ClassWithLocalClass.m2",
                                "ClassWithLocalClass.m3",
                                "ClassWithLocalClass.mo",
                                "ClassWithLocalClass.mo",
                                "ClassWithLocalClass.LocalClass.m1",
                                "ClassWithLocalClass.LocalClass.m2",
                                "ClassWithLocalClass.LocalClass.m3",
                                "ClassWithLocalClass.LocalClass.mo",
                                "ClassWithLocalClass.LocalClass.mo");

        testAllInnerMethodsOf("/JavaClasses/Interface.java",
                "Interface.m1",
                                "Interface.m2",
                                "Interface.m3",
                                "Interface.m4",
                                "Interface.m5",
                                "Interface.m6",
                                "Interface.mo",
                                "Interface.mo");
    }

    private void testAllInnerMethodsOf(final @NotNull String fileName,
                                      final @NotNull String... expectedMethods) throws Exception {
        File file = addSourceFileToProjectDir(fileName);
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance()
                        .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                            JavaMethod.creator)
                        .stream()
                        .map(JavaMethod::qualifiedName)
                        .collect(Collectors.toList()),
                containsInAnyOrder(expectedMethods));
    }
}