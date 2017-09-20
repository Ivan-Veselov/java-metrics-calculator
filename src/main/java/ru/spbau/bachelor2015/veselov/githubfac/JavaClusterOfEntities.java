package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
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
     * Returns a textual representation of cluster's AST.
     */
    default @NotNull String textualAst() {
        // TODO: rewrite with standard visitor which may have more proper way of traversing
        class TextBuilder {
            private static final int INDENT_INC_SIZE = 2;

            private final @NotNull StringBuilder stringBuilder = new StringBuilder();

            public TextBuilder(final @NotNull Node node) {
                traverse(node, 0);
            }

            public @NotNull String getText() {
                return stringBuilder.toString();
            }

            private void traverse(final @NotNull Node node, final int indent) {
                stringBuilder.append(StringUtils.repeat(' ', indent))
                        .append(node.getClass().getSimpleName());

                if (node.getChildNodes().isEmpty()) {
                    stringBuilder.append('(')
                            .append(node)
                            .append(')');
                }

                stringBuilder.append('\n');

                for (Node child : node.getChildNodes()) {
                    traverse(child, indent + INDENT_INC_SIZE);
                }
            }
        }

        return new TextBuilder(clusterNode()).getText();
    }

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
