package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

/**
 * An interface which represents a Java entity that stores other Java entities inside itself.
 * Cluster has definite position in source code and know the context of the project. Typical
 * examples of clusters are: classes, methods, blocks of code.
 */
public interface JavaClusterOfEntities {
    /**
     * Returns an AST node which represents part of source code that corresponds to cluster.
     */
    @NotNull Node clusterNode();

    /**
     * Returns cluster project context.
     */
    @NotNull JavaParserTypeSolver clusterTypeSolver();

    /**
     * Return number of code lines which cluster consists of.
     */
    default int numberOfCodeLines() {
        int lines = 1;

        // TODO: throw an exception if null?
        for (JavaToken token : clusterNode().getTokenRange().get()) {
            if (token.getCategory().isEndOfLine()) {
                lines++;
            }
        }

        return lines;
    }
}
