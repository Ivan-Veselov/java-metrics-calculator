package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.junit.Test;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class JavaClusterOfEntitiesTest extends TestEnvironment {
    @Test
    public void textualAstOfTest() throws Exception {
        File file = addToProjectDir("/JavaClasses/PrintMe.java");
        JavaParserTypeSolver javaParserTypeSolver = getNewTypeSolver();
        CompilationUnit unit = JavaParser.parse(file);

        assertThat(new DullJavaClusterOfEntities(unit, javaParserTypeSolver).textualAst(),
                is(equalTo(astFrom("/AST/PrintMe"))));
    }
}