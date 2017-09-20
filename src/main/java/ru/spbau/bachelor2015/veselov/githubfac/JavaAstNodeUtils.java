package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

public class JavaAstNodeUtils {
    private static JavaAstNodeUtils ourInstance = new JavaAstNodeUtils();

    public static JavaAstNodeUtils getInstance() {
        return ourInstance;
    }

    private JavaAstNodeUtils() {
    }

    public @NotNull String textualAstOf(final @NotNull Node node) {
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

        return new TextBuilder(node).getText();
    }

    public int codeLinesNumberIn(final @NotNull Node node) {
        int lines = 1;

        // TODO: throw an exception if null?
        for (JavaToken token : node.getTokenRange().get()) {
            if (token.getCategory().isEndOfLine()) {
                lines++;
            }
        }

        return lines;
    }

    // TODO: Should be moved to project entity as it has connection with project context.
    public <T> @NotNull List<T> allInnerEntitiesOf(
                            final @NotNull JavaClusterOfEntities cluster,
                            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator) {
        return cluster.clusterNode().accept(creator, cluster.clusterTypeSolver());
    }

    // TODO: need some test
    // TODO: Should be moved to project entity as it has connection with project context.
    // TODO: need generalization
    // TODO: for methods this function should return a list because of overloading
    public @NotNull Optional<JavaMethod> methodByQualifiedNameIn(
                                         final @NotNull JavaClusterOfEntities cluster,
                                         final @NotNull String methodName) {
        List<JavaMethod> list = allInnerEntitiesOf(cluster, JavaMethod.creator);
        for (JavaMethod method : list) {
            if (method.qualifiedName().equals(methodName)) {
                return Optional.of(method);
            }
        }

        return Optional.empty();
    }
}
