package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(final @NotNull String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("Path to a source folder expected.");
            return;
        }

        JavaProjectSourceFolder sourceFolder = new JavaProjectSourceFolder(Paths.get(args[0]));

        System.out.println(
                "Write down fully qualified names of methods which AST should be printed.");
        System.out.println("(EOL is a separator. Explicit EOF denotes the end of this list.)");

        List<String> methodsNames = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String methodName = reader.readLine();
            while (methodName != null) {
                methodsNames.add(methodName);
                methodName = reader.readLine();
            }
        }

        for (String name : methodsNames) {
            List<JavaMethod> methods = sourceFolder.allMethodsByQualifiedName(name);
            if (methods.isEmpty()) {
                System.out.println("No such method: " + name);
                continue;
            }

            for (int i = 0; i < methods.size(); i++) {
                FileUtils.writeStringToFile(new File(name + "$" + i),
                                            methods.get(i).textualAst(),
                                            (Charset) null);
            }
        }

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
