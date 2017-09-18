package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class JavaMethodDeclaration extends JavaAstNode {
    private final @NotNull MethodDeclaration methodDeclaration;

    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull JavaParserMethodDeclaration solvedMethodDeclaration;

    public static final @NotNull JavaAstNodeFactory<JavaMethodDeclaration> factory =
            (node, javaParserTypeSolver) -> {
                if (!node.getClass().equals(MethodDeclaration.class)) {
                    return Optional.empty();
                }

                MethodDeclaration methodDeclaration = (MethodDeclaration) node;
                return Optional.of(
                        new JavaMethodDeclaration(methodDeclaration, javaParserTypeSolver));
            };

    public JavaMethodDeclaration(final @NotNull MethodDeclaration methodDeclaration,
                                 final @NotNull JavaParserTypeSolver javaParserTypeSolver) {
        this.methodDeclaration = methodDeclaration;
        this.javaParserTypeSolver = javaParserTypeSolver;

        solvedMethodDeclaration = new JavaParserMethodDeclaration(methodDeclaration,
                                                                  javaParserTypeSolver);
    }

    @Override
    public @NotNull Node getNode() {
        return methodDeclaration;
    }

    @Override
    public @NotNull JavaParserTypeSolver getJavaParserTypeSolver() {
        return javaParserTypeSolver;
    }

    public @NotNull MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public @NotNull JavaParserMethodDeclaration getSolvedMethodDeclaration() {
        return solvedMethodDeclaration;
    }

    public @NotNull String getQualifiedName() {
        return solvedMethodDeclaration.getQualifiedName();
    }
}
