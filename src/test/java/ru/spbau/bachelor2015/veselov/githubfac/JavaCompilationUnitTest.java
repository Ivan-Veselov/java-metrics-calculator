package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class JavaCompilationUnitTest extends TestEnvironment {
    @Test
    public void getMethodsDeclarationsTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/Methods.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getJavaCompilationUnit(file, typeSolver);

        /*
            TODO: Invalid test.
            Actually two method (one of anonymous class and one of function local class, both are
            situated inside function body) are missed. Seems like it's a problem of JavaParser.
        */
        assertThat(compilationUnit.getMethodsDeclarations()
                                  .stream()
                                  .map(JavaMethodDeclaration::getQualifiedName)
                                  .collect(Collectors.toList()),
                   containsInAnyOrder("Interface.method0",
                                             "Methods.method1",
                                             "Methods.method2",
                                             "Methods.method3",
                                             "Methods.method4",
                                             "Methods.InnerClass.method5",
                                             "HiddenClass.method6"));
    }
}
