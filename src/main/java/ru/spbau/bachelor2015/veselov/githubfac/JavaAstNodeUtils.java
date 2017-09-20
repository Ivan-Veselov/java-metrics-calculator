package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

// TODO: all this methods should be put in some more appropriate place.
public class JavaAstNodeUtils {
    private static JavaAstNodeUtils ourInstance = new JavaAstNodeUtils();

    public static JavaAstNodeUtils getInstance() {
        return ourInstance;
    }

    private JavaAstNodeUtils() {
    }

    // TODO: make entities creators which will be passed to this method
    public <T> @NotNull List<T> allInnerEntitiesOf(
                            final @NotNull JavaClusterOfEntities cluster,
                            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator) {
        return cluster.clusterNode().accept(creator, cluster.clusterTypeSolver());
    }

    // TODO: need generalization
    public @NotNull List<JavaMethod> methodByQualifiedNameIn(
                                         final @NotNull JavaClusterOfEntities cluster,
                                         final @NotNull String methodName) {
        return allInnerEntitiesOf(cluster, JavaMethod.creator)
                    .stream()
                    .filter(m -> m.qualifiedName().equals(methodName))
                    .collect(Collectors.toList());
    }
}
