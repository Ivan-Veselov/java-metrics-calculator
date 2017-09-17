package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class JavaMethodDeclarationTest extends TestEnvironment {
    @Test
    public void getTextualASTTest() throws Exception {
        File file = addSourceFileToProjectDir("/ClassWithMethodToPrint.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getJavaCompilationUnit(file, typeSolver);

        List<JavaMethodDeclaration> methodsDeclarations = compilationUnit.getMethodsDeclarations();
        assertThat(methodsDeclarations.size(), is(equalTo(1)));

        JavaMethodDeclaration methodDeclaration = methodsDeclarations.get(0);
        System.out.println(methodDeclaration.getTextualAST());
    }
}
