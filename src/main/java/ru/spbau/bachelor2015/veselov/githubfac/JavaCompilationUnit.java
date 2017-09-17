package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.CompilationUnit;
import org.jetbrains.annotations.NotNull;

public final class JavaCompilationUnit {
    private final @NotNull CompilationUnit compilationUnit;

    public JavaCompilationUnit(final @NotNull CompilationUnit compilationUnit) {
        this.compilationUnit = compilationUnit;
    }

    public @NotNull CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }
}
