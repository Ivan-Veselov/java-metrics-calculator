package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.FileVisitResult.CONTINUE;

public class JavaProjectSourceFolder implements JavaEntitiesCluster {
    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull List<JavaCompilationUnit> compilationUnits = new ArrayList<>();

    // TODO: check that files have been parsed.
    // TODO: check that files structure corresponds to packages structure.
    public JavaProjectSourceFolder(final @NotNull Path path) throws IOException {
        javaParserTypeSolver = new JavaParserTypeSolver(path.toFile());

        Files.walkFileTree(path,
            new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(final Path path, final BasicFileAttributes attrs)
                                                                                throws IOException {
                    CompilationUnit unit = JavaParser.parse(path.toFile());
                    compilationUnits.add(new JavaCompilationUnit(javaParserTypeSolver, unit));

                    return CONTINUE;
                }
            }
        );
    }

    public @NotNull List<JavaCompilationUnit> getCompilationUnits() {
        return new ArrayList<>(compilationUnits);
    }

    @Override
    public @NotNull List<Node> cluster() {
        return compilationUnits
                .stream()
                .map(JavaCompilationUnit::holderNode)
                .collect(Collectors.toList());
    }

    @Override
    public @NotNull JavaParserTypeSolver clusterTypeSolver() {
        return javaParserTypeSolver;
    }

    public class JavaCompilationUnit implements JavaEntitiesHolder {
        private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

        private final @NotNull CompilationUnit compilationUnit;

        private JavaCompilationUnit(final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                                    final @NotNull CompilationUnit compilationUnit) {
            this.compilationUnit = compilationUnit;
            this.javaParserTypeSolver = javaParserTypeSolver;
        }

        @Override
        public @NotNull Node holderNode() {
            return compilationUnit;
        }

        @Override
        public @NotNull JavaParserTypeSolver clusterTypeSolver() {
            return javaParserTypeSolver;
        }
    }
}
