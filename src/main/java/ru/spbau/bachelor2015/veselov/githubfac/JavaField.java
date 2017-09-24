package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.visitor.GenericListVisitorAdapter;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// TODO: might be a good ability to receive field fully qualified name.
public class JavaField implements JavaNamedEntity {
    private final @NotNull JavaParserTypeSolver javaParserTypeSolver;

    private final @NotNull VariableDeclarator variableDeclarator;

    public static final @NotNull GenericVisitor<List<JavaField>, JavaParserTypeSolver> creator =
        new GenericListVisitorAdapter<JavaField, JavaParserTypeSolver>() {
            @Override
            public List<JavaField> visit(final FieldDeclaration n,
                                         final JavaParserTypeSolver javaParserTypeSolver) {
                List<JavaField> list = super.visit(n, javaParserTypeSolver);
                if (list == null) {
                    list = new ArrayList<>();
                }

                for (VariableDeclarator declarator : n.getVariables()) {
                    list.add(new JavaField(javaParserTypeSolver, declarator));
                }

                return list;
            }
        };

    private JavaField(final @NotNull JavaParserTypeSolver javaParserTypeSolver,
                     final @NotNull VariableDeclarator variableDeclarator) {
        this.javaParserTypeSolver = javaParserTypeSolver;
        this.variableDeclarator = variableDeclarator;
    }

    @Override
    public @NotNull String simpleName() {
        return variableDeclarator.getNameAsString();
    }
}
