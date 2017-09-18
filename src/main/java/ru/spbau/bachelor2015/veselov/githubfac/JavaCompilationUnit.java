package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.GenericVisitorAdapter;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // WARNING: copy-paste won't be generification. Visitor depends on CompilationUnit.
    public @NotNull List<JavaMethodDeclaration> getMethodsDeclarations() {
        GenericVisitorAdapter<List<JavaMethodDeclaration>, Void> visitor =
        new GenericVisitorAdapter<List<JavaMethodDeclaration>, Void>() {
            private final @NotNull ArrayList<JavaMethodDeclaration> list = new ArrayList<>();

            public @Override @NotNull List<JavaMethodDeclaration> visit(final CompilationUnit node,
                                                                        final Void arg) {
                super.visit(node, arg);
                return list;
            }

            @Override
            public @Nullable List<JavaMethodDeclaration> visit(final MethodDeclaration node,
                                                               final Void arg) {
                list.add(new JavaMethodDeclaration(node, typeSolver));
                return null;
            }
        };

        return compilationUnit.accept(visitor, null);
    }

    public @NotNull Optional<JavaMethodDeclaration> methodDeclarationByName(
                                                            final @NotNull String methodName) {
        GenericVisitorAdapter<JavaMethodDeclaration, Void> visitor =
        new GenericVisitorAdapter<JavaMethodDeclaration, Void>() {
            @Override
            public @Nullable JavaMethodDeclaration visit(final MethodDeclaration node,
                                                               final Void arg) {
                JavaMethodDeclaration methodDeclaration =
                        new JavaMethodDeclaration(node, typeSolver);

                if (methodDeclaration.getQualifiedName().equals(methodName)) {
                    return methodDeclaration;
                }

                return null;
            }
        };

        return Optional.ofNullable(compilationUnit.accept(visitor, null));
    }
}
