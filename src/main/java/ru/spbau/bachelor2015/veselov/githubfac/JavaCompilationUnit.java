package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

public class JavaCompilationUnit implements JavaClusterOfEntities {
    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull CompilationUnit compilationUnit;

    // TODO: need to hide ctor, but project entity must have access to it.
    public JavaCompilationUnit(final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                                final @NotNull CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
        this.javaParserTypeSolver = javaParserTypeSolver;
    }

    @Override
    public @NotNull Node clusterNode() {
        return compilationUnit;
    }

    @Override
    public @NotNull JavaParserTypeSolver clusterTypeSolver() {
        return javaParserTypeSolver;
    }
}
