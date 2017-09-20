package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
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
}