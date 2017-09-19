package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

    protected @NotNull String astFrom(final @NotNull String fileName) throws Exception {
        Path pathToFile = Paths.get(getClass().getResource(fileName).getFile());
        return FileUtils.readFileToString(pathToFile.toFile(), (Charset) null);
    }

    protected @NotNull JavaClusterOfEntities mockCluster(
                                     final @NotNull Node node,
                                     final @NotNull JavaParserTypeSolver javaParserTypeSolver) {
        JavaClusterOfEntities cluster = mock(JavaClusterOfEntities.class);

        when(cluster.clusterNode()).thenReturn(node);
        when(cluster.clusterTypeSolver()).thenReturn(javaParserTypeSolver);

        return cluster;
    }
}
