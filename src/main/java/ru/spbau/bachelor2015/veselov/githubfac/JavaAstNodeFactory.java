package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface JavaAstNodeFactory<T extends JavaAstNode> {
    @NotNull Optional<T> create(final @NotNull Node node,
                                final @NotNull JavaParserTypeSolver javaParserTypeSolver);
}
