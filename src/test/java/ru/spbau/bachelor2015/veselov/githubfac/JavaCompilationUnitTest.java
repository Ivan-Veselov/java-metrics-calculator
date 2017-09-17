package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import javaslang.collection.List;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class JavaCompilationUnitTest {
    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    @Test
    public void getMethodsDeclarationsTest() throws Exception {
        File file = addSourceFileToProjectDir("/Methods.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getCompilationUnit(file, typeSolver);

        /*
            TODO: Invalid test.
            Actually one method of anonymous class is missed. Seems like it's a problem of
            JavaParser.
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

    private @NotNull File addSourceFileToProjectDir(final @NotNull String fileName)
                                                                                throws Exception {
        Path pathToFile = Paths.get(getClass().getResource(fileName).getFile());

        File newFile = projectFolder.newFile(pathToFile.getFileName().toString());
        FileUtils.copyFile(pathToFile.toFile(), newFile);

        return newFile;
    }

    private @NotNull JavaParserTypeSolver getNewTypeSolver() {
        return new JavaParserTypeSolver(projectFolder.getRoot());
    }

    private @NotNull JavaCompilationUnit getCompilationUnit(final @NotNull File file,
                                final @NotNull JavaParserTypeSolver typeSolver) throws Exception {
        return new JavaCompilationUnit(JavaParser.parse(file), typeSolver);
    }
}
