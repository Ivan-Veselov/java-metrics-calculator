package ru.spbau.bachelor2015.veselov.githubfac;

import com.github.javaparser.JavaToken;
import com.github.javaparser.ast.Node;
import com.github.javaparser.printer.JsonPrinter;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * An interface which represents a Java entity that stores other Java entities inside itself.
 * Holder has definite position in source code and know the context of the project. Typical
 * examples of holders are: classes, methods, blocks of code.
 */
public interface JavaEntitiesHolder extends JavaEntitiesCluster {
    /**
     * Returns an AST node which represents part of source code that corresponds to holder.
     */
    @NotNull Node holderNode();

    @Override
    default  @NotNull List<Node> cluster() {
        return Collections.singletonList(holderNode());
    }

    /**
     * Returns a JSON representation of holder's AST.
     */
    default @NotNull String jsonAst() {
        String string = new JsonPrinter(true).output(holderNode());
        JsonElement element = new JsonParser().parse(string);

        return new GsonBuilder().setPrettyPrinting().create().toJson(element);
    }

    /**
     * Returns number of code lines which holder consists of.
     */
    // TODO: this method doesn't count comments.
    default int numberOfCodeLines() {
        int lines = 1;

        // TODO: throw an exception if null?
        for (JavaToken token : holderNode().getTokenRange().get()) {
            if (token.getCategory().isEndOfLine()) {
                lines++;
            }
        }

        return lines;
    }
}
