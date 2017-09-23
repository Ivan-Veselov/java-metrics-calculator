package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A class which represents a bunch of java entities. A typical example is compilation units in a
 * project.
 */
public interface JavaEntitiesCluster {
    /**
     * Returns an AST nodes which this cluster consists of.
     */
    @NotNull List<Node> cluster();

    /**
     * Returns cluster's project context.
     */
    @NotNull JavaParserTypeSolver clusterTypeSolver();

    /**
     * Runs a visitor that creates entities on all nodes that are inside this cluster and returns
     * a resulting list.
     *
     * @param creator an AST visitor which creates a list of specific entities that are inside this
     *                cluster
     * @param <T> a type of entities to create.
     * @return a list that was constructed by creator.
     */
    // TODO: make entities creators type which will be a type of this method argument
    default <T> @NotNull List<T> allInnerEntities(
            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator) {
        return cluster()
                .stream()
                .map(n -> n.accept(creator, clusterTypeSolver()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Finds and returns all overloads of a method with a given qualified name that are inside this
     * cluster.
     *
     * @param methodName a qualified name of a method.
     * @return a list of all methods with given name that are inside this cluster.
     */
    // TODO: need generalization
    default @NotNull List<JavaMethod> allMethodsByQualifiedName(final @NotNull String methodName) {
        return allInnerEntities(JavaMethod.creator)
                .stream()
                .filter(m -> m.qualifiedName().equals(methodName))
                .collect(Collectors.toList());
    }
}
