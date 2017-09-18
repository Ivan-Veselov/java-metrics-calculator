package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

public final class JavaCompilationUnit extends JavaAstNode {
    private final @NotNull CompilationUnit compilationUnit;

    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    public JavaCompilationUnit(final @NotNull CompilationUnit compilationUnit,
                               final @NotNull JavaParserTypeSolver javaParserTypeSolver) {
        this.compilationUnit = compilationUnit;
        this.javaParserTypeSolver = javaParserTypeSolver;
    }

    @Override
    public @NotNull Node getNode() {
        return compilationUnit;
    }

    @Override
    public @NotNull JavaParserTypeSolver getJavaParserTypeSolver() {
        return javaParserTypeSolver;
    }

    public @NotNull CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
}
