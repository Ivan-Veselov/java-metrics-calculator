package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JavaCompilationUnitTest {
    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    @Test
    public void getMethods() throws Exception {
        File file = addSourceFileToProjectDir("/Methods.java");
        JavaParserTypeSolver typeSolver = getNewTypeSolver();
        JavaCompilationUnit compilationUnit = getCompilationUnit(file, typeSolver);

        for (MethodDeclaration method : compilationUnit.getMethodsDeclarations()) {
            JavaParserMethodDeclaration jpmd = new JavaParserMethodDeclaration(method, typeSolver);
            System.out.println(jpmd.getQualifiedName());
        }
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
