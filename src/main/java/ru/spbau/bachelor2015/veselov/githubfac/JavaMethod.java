package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavaMethod {
    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull MethodDeclaration methodDeclaration;

    private final @NotNull JavaParserMethodDeclaration solvedMethodDeclaration;

    public static final @NotNull GenericVisitor<List<JavaMethod>, JavaParserTypeSolver> creator =
        new GenericListVisitorAdapter<JavaMethod, JavaParserTypeSolver>() {
            @Override
            public List<JavaMethod> visit(final MethodDeclaration n,
                                          final JavaParserTypeSolver javaParserTypeSolver) {
                List<JavaMethod> list = new ArrayList<>();
                list.add(new JavaMethod(javaParserTypeSolver, n));

                return list;
            }
        };

    private JavaMethod(final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                      final @NotNull MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
        this.javaParserTypeSolver = javaParserTypeSolver;

        solvedMethodDeclaration = new JavaParserMethodDeclaration(methodDeclaration,
                                                                  javaParserTypeSolver);
    }

    public @NotNull String qualifiedName() {
        return solvedMethodDeclaration.getQualifiedName();
    }
}
