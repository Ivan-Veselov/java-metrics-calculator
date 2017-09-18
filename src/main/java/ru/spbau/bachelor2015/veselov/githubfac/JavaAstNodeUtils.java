package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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

    // TODO: need some test
    // TODO: Should be moved to project entity as it has connection with project context.
    public @NotNull Optional<JavaMethod> methodByQualifiedNameIn(
                                         final @NotNull Node node,
                                         final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                                         final @NotNull String methodName) {
        GenericVisitorAdapter<JavaMethod, Void> visitor =
            new GenericVisitorAdapter<JavaMethod, Void>() {
                @Override
                public @Nullable JavaMethod visit(final MethodDeclaration node, final Void arg) {
                    JavaMethod method = new JavaMethod(javaParserTypeSolver, node);

                    if (method.getQualifiedName().equals(methodName)) {
                        return method;
                    }

                    return null;
                }
            };

        return Optional.ofNullable(node.accept(visitor, null));
    }

    // TODO: Should be moved to project entity as it has connection with project context.
    public <T> @NotNull List<T> allInnerEntitiesOf(
                            final @NotNull Node node,
                            final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator) {
        return node.accept(creator, javaParserTypeSolver);
    }
}
