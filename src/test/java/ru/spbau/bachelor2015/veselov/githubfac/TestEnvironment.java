package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestEnvironment {
    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    protected @NotNull File addSourceFileToProjectDir(final @NotNull String fileName)
                                                                                throws Exception {
        Path pathToFile = Paths.get(getClass().getResource(fileName).getFile());

        File newFile = projectFolder.newFile(pathToFile.getFileName().toString());
        FileUtils.copyFile(pathToFile.toFile(), newFile);

        return newFile;
    }

    protected @NotNull JavaParserTypeSolver getNewTypeSolver() {
        return new JavaParserTypeSolver(projectFolder.getRoot());
    }

    protected @NotNull JavaCompilationUnit getJavaCompilationUnit(final @NotNull File file,
                                  final @NotNull JavaParserTypeSolver typeSolver) throws Exception {
        return new JavaCompilationUnit(JavaParser.parse(file), typeSolver);
    }

    protected @NotNull String astFrom(final @NotNull String fileName) throws Exception {
        Path pathToFile = Paths.get(getClass().getResource(fileName).getFile());
        return FileUtils.readFileToString(pathToFile.toFile(), (Charset) null);
    }

    protected @NotNull JavaMethodDeclaration methodDeclarationByName(
                                            final @NotNull JavaCompilationUnit compilationUnit,
                                            final @NotNull String methodName) {
        Optional<JavaMethodDeclaration> optional =
                                            compilationUnit.methodDeclarationByName(methodName);

        assertThat(optional.isPresent(), is(true));
        return optional.get();
    }
}
