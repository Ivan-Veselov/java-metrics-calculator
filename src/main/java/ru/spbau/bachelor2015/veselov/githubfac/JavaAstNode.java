package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class JavaAstNode {
    public abstract @NotNull Node getNode();

    public abstract @NotNull JavaParserTypeSolver getJavaParserTypeSolver();

    public @NotNull String textualAST() {
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

        return new TextBuilder(getNode()).getText();
    }

    public int codeLength() {
        int length = 0;

        // TODO: throw an exception if null?
        for (JavaToken token : getNode().getTokenRange().get()) {
            length += token.asString().length();
        }

        return length;
    }

    // TODO: need some test
    public @NotNull Optional<JavaMethodDeclaration> methodDeclarationByName(
                                                             final @NotNull String methodName) {
        GenericVisitorAdapter<JavaMethodDeclaration, Void> visitor =
            new GenericVisitorAdapter<JavaMethodDeclaration, Void>() {
                @Override
                public @Nullable JavaMethodDeclaration visit(final MethodDeclaration node,
                                                             final Void arg) {
                    JavaMethodDeclaration methodDeclaration =
                            new JavaMethodDeclaration(node, getJavaParserTypeSolver());

                    if (methodDeclaration.getQualifiedName().equals(methodName)) {
                        return methodDeclaration;
                    }

                    return null;
                }
            };

        return Optional.ofNullable(getNode().accept(visitor, null));
    }

    public @NotNull List<JavaMethodDeclaration> allInsideMethodsDeclarations() {
        class Fetcher extends VoidVisitorAdapter<Void> {
            private final @NotNull ArrayList<JavaMethodDeclaration> list = new ArrayList<>();

            @Override
            public void visit(final MethodDeclaration node, final Void arg) {
                list.add(new JavaMethodDeclaration(node, getJavaParserTypeSolver()));
            }
        }

        Fetcher fetcher = new Fetcher();
        getNode().accept(fetcher, null);
        return fetcher.list;
    }
}
