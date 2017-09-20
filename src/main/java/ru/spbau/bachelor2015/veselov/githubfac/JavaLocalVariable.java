package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// TODO: looks very similar to JavaField. There may be a place for generalization.
public class JavaLocalVariable {
    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull VariableDeclarator variableDeclarator;

    public static final
                @NotNull GenericVisitor<List<JavaLocalVariable>, JavaParserTypeSolver> creator =
        new GenericListVisitorAdapter<JavaLocalVariable, JavaParserTypeSolver>() {
            @Override
            public List<JavaLocalVariable> visit(final VariableDeclarationExpr n,
                                                 final JavaParserTypeSolver javaParserTypeSolver) {
                List<JavaLocalVariable> list = super.visit(n, javaParserTypeSolver);
                if (list == null) {
                    list = new ArrayList<>();
                }

                for (VariableDeclarator declarator : n.getVariables()) {
                    list.add(new JavaLocalVariable(javaParserTypeSolver, declarator));
                }

                return list;
            }
        };

    private JavaLocalVariable(final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                             final @NotNull VariableDeclarator variableDeclarator) {
        this.javaParserTypeSolver = javaParserTypeSolver;
        this.variableDeclarator = variableDeclarator;
    }

    public @NotNull String simpleName() {
        return variableDeclarator.getNameAsString();
    }
}
