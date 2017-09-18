package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

public class JavaMethodDeclaration {
    private final @NotNull MethodDeclaration methodDeclaration;

    private final @NotNull JavaParserTypeSolver typeSolver;

    private final @NotNull JavaParserMethodDeclaration solvedMethodDeclaration;

    public JavaMethodDeclaration(final @NotNull MethodDeclaration methodDeclaration,
                                 final @NotNull JavaParserTypeSolver typeSolver) {
        this.methodDeclaration = methodDeclaration;
        this.typeSolver = typeSolver;

        solvedMethodDeclaration = new JavaParserMethodDeclaration(methodDeclaration, typeSolver);
    }

    public @NotNull MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public @NotNull JavaParserTypeSolver getTypeSolver() {
        return typeSolver;
    }

    public @NotNull JavaParserMethodDeclaration getSolvedMethodDeclaration() {
        return solvedMethodDeclaration;
    }

    public @NotNull String getQualifiedName() {
        return solvedMethodDeclaration.getQualifiedName();
    }

    public @NotNull String getTextualAST() {
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

        return new TextBuilder(methodDeclaration).getText();
    }

    public int codeLength() {
        int length = 0;

        // TODO: throw an exception if null?
        for (JavaToken token : methodDeclaration.getTokenRange().get()) {
            length += token.asString().length();
        }

        return length;
    }
}
