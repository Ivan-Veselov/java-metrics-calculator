package ru.spbau.bachelor2015.veselov.githubfac;

import org.junit.Test;

import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;

public class JavaProjectSourceFolderTest extends TestEnvironment {
    @Test
    public void testCreation() throws Exception {
        addToProjectDir("/JavaClasses/com");
        JavaProjectSourceFolder srcFolder =
                                    new JavaProjectSourceFolder(projectFolder.getRoot().toPath());
        assertThat(srcFolder.getCompilationUnits().size(), is(equalTo(3)));
    }
}