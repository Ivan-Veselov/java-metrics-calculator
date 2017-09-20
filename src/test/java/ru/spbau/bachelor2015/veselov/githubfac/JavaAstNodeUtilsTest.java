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

// TODO: subdivide tests on different groups with different possible classes type, fields type, etc.
// TODO: add test with unsolved name of anonymous class method.
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

        assertThat(JavaAstNodeUtils.getInstance()
                                   .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                                       JavaMethod.creator)
                                   .stream()
                                   .map(JavaMethod::qualifiedName)
                                   .collect(Collectors.toList()),
                containsInAnyOrder("Interface.method0",
                                          "Methods.method1",
                                          "Methods.method2",
                                          "Methods.method3",
                                          "Methods.method4",
                                          "Methods.InnerClass.method5",
                                          "HiddenClass.method6",
                                          "HiddenClass.MethodClass.method7"));
    }

    @Test
    public void allInnerFieldsOfTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/Variables.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance()
                                   .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                                       JavaField.creator)
                                   .stream()
                                   .map(JavaField::simpleName)
                                   .collect(Collectors.toList()),
                   containsInAnyOrder("field1", "field2"));
    }

    @Test
    public void allInnerLocalVariablesOfTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/Variables.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(JavaAstNodeUtils.getInstance()
                                   .allInnerEntitiesOf(mockCluster(unit, typeSolver),
                                                       JavaLocalVariable.creator)
                                   .stream()
                                   .map(JavaLocalVariable::simpleName)
                                   .collect(Collectors.toList()),
                containsInAnyOrder("c", "d"));
    }
}