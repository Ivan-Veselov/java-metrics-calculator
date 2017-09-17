package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class JavaCompilationUnit {
    private final @NotNull CompilationUnit compilationUnit;

    private final @NotNull JavaParserTypeSolver typeSolver;

    public JavaCompilationUnit(final @NotNull CompilationUnit compilationUnit,
                               final @NotNull JavaParserTypeSolver typeSolver) {
        this.compilationUnit = compilationUnit;
        this.typeSolver = typeSolver;
    }

    public @NotNull CompilationUnit getCompilationUnit() {
        return compilationUnit;
    }

    public @NotNull JavaParserTypeSolver getTypeSolver() {
        return typeSolver;
    }

    public @NotNull List<MethodDeclaration> getMethods() {
        GenericVisitorAdapter<List<MethodDeclaration>, Void> visitor =
        new GenericVisitorAdapter<List<MethodDeclaration>, Void>() {
            private final @NotNull ArrayList<MethodDeclaration> list = new ArrayList<>();

            public @Override @NotNull List<MethodDeclaration> visit(final CompilationUnit node,
                                                                    final Void arg) {
                super.visit(node, arg);
                return list;
            }

            @Override
            public @Nullable List<MethodDeclaration> visit(final MethodDeclaration node,
                                                           final Void arg) {
                list.add(node);
                return null;
            }
        };

        return compilationUnit.accept(visitor, null);
    }
}
