package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import javax.swing.tree.ExpandVetoException;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class JavaMethodDeclarationTest extends TestEnvironment {
    @Test
    public void getTextualASTTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/ClassWithMethodToPrint.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getJavaCompilationUnit(file, typeSolver);

        JavaMethodDeclaration methodDeclaration = methodDeclarationByName(compilationUnit,
                                                        "ClassWithMethodToPrint.gcd");
        assertThat(methodDeclaration.getTextualAST(),
                is(equalTo(astFrom("/AST/ClassWithMethodToPrint.gcd"))));
    }

    @Test
    public void codeLengthTest() throws Exception {
        File file = addSourceFileToProjectDir("/JavaClasses/MethodsLength.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getJavaCompilationUnit(file, typeSolver);

        assertMethodLength(compilationUnit, "MethodsLength.method1", 118);
        assertMethodLength(compilationUnit, "MethodsLength.method2", 119);
        assertMethodLength(compilationUnit, "MethodsLength.method3", 112);
        assertMethodLength(compilationUnit, "MethodsLength.method4", 120);
        assertMethodLength(compilationUnit, "MethodsLength.method5", 133);
        assertMethodLength(compilationUnit, "MethodsLength.method6", 106);
        assertMethodLength(compilationUnit, "MethodsLength.method7", 123);
    }

    private void assertMethodLength(final @NotNull JavaCompilationUnit compilationUnit,
                                    final @NotNull String methodName,
                                    final int expectedLength) throws Exception {
        JavaMethodDeclaration methodDeclaration = methodDeclarationByName(compilationUnit,
                                                                          methodName);
        assertThat(methodDeclaration.codeLength(), is(equalTo(expectedLength)));
    }
}
