package ru.spbau.bachelor2015.veselov.githubfac;

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

public class TestEnvironment {
    @Rule
    public TemporaryFolder projectFolder = new TemporaryFolder();

    protected @NotNull File addToProjectDir(final @NotNull String location) throws Exception {
        Path pathToFile = Paths.get(getClass().getResource(location).getFile());
        File file = pathToFile.toFile();

        if (file.isDirectory()) {
            File newDir = projectFolder.newFolder(pathToFile.getFileName().toString());
            FileUtils.copyDirectory(file, newDir);
            return newDir;
        }

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

    protected static class DullJavaEntitiesHolder implements JavaEntitiesHolder {
        private final Node node;

        private final JavaParserTypeSolver javaParserTypeSolver;

        public DullJavaEntitiesHolder(final Node node,
                                      final JavaParserTypeSolver javaParserTypeSolver) {
            this.node = node;
            this.javaParserTypeSolver = javaParserTypeSolver;
        }

        @Override
        public @NotNull Node holderNode() {
            return node;
        }

        @Override
        public @NotNull JavaParserTypeSolver clusterTypeSolver() {
            return javaParserTypeSolver;
        }
    }
}
