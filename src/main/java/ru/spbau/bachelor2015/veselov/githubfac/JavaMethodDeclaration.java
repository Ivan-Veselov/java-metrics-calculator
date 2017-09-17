package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
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
}
