package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(final @NotNull String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Path to a source folder expected.");
            return;
        }

        JavaProjectSourceFolder sourceFolder = new JavaProjectSourceFolder(Paths.get(args[0]));

        printLengthCharacteristicOf("Methods", JavaMethod.creator, sourceFolder);
        printNameCharacteristicOf("Fields", JavaField.creator, sourceFolder);
        printNameCharacteristicOf("Local variables",
                                                        JavaLocalVariable.creator, sourceFolder);
    }

    public static <T extends JavaEntitiesHolder> void printLengthCharacteristicOf(
                            final @NotNull String entityName,
                            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator,
                            final @NotNull JavaProjectSourceFolder sourceFolder) {
        List<T> entities = sourceFolder.allInnerEntities(creator);

        int numberOf = entities.size();
        double meanLength = (double) entities
                                        .stream()
                                        .map(JavaEntitiesHolder::numberOfCodeLines)
                                        .mapToInt(i -> i)
                                        .sum() / numberOf;

        System.out.println(entityName + " number: " + numberOf);
        System.out.println(entityName + " mean length: " + meanLength);
    }

    public static <T extends JavaNamedEntity> void printNameCharacteristicOf(
                            final @NotNull String entityName,
                            final @NotNull GenericVisitor<List<T>, JavaParserTypeSolver> creator,
                            final @NotNull JavaProjectSourceFolder sourceFolder) {
        List<T> entities = sourceFolder.allInnerEntities(creator);

        int numberOf = entities.size();
        double meanNameLength = (double) entities
                                            .stream()
                                            .map(JavaNamedEntity::simpleName)
                                            .map(String::length)
                                            .mapToInt(i -> i)
                                            .sum() / numberOf;

        System.out.println(entityName + " number: " + numberOf);
        System.out.println(entityName + " mean name length: " + meanNameLength);
    }
}
