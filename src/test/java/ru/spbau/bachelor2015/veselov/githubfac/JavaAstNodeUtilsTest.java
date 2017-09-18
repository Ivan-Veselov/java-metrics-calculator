package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class JavaAstNodeUtilsTest extends TestEnvironment {
    @Test
    public void textualAstOfTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/ClassToPrint.java");
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance().textualAstOf(unit),
                is(equalTo(astFrom("/AST/ClassToPrint"))));
    }

    @Test
    public void numberOfCodeLinesInTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/ClassLength.java");
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance().codeLinesNumberIn(unit),
                                                                        is(equalTo(7)));
    }

    @Test
    public void allInnerMethodsOfTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/Methods.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        /*
            TODO: Invalid test.
            Actually two method (one of anonymous class and one of function local class, both are
            situated inside function body) are missed. Seems like it's a problem of JavaParser.
            If to go through nodes manually, this methods will be found but symbol solver won't
            be able to deal with them.
        */
        assertThat(JavaAstNodeUtils.getInstance()
                                   .allInnerEntitiesOf(unit, typeSolver, JavaMethod.creator)
                                   .stream()
                                   .map(JavaMethod::getQualifiedName)
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