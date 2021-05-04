package Reactive;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Parameter {

	private final String directory;
    private final String ignoredPath;
    private final int nWords;
    private final Set<String> ignoredWords = new HashSet<>();

    public Parameter(final String directory, final String path, final int nWords) {
        this.directory = directory;
        this.ignoredPath = path;
        this.nWords = nWords;
    }

    public List<File> getFilesInDirectory() {
        final File dirFile = new File(this.directory);
        return Arrays.asList(Objects.requireNonNull(dirFile.listFiles()));
    }

    public Set<String> getIgnoredWords() {
        return this.ignoredWords;
    }

    public int getNWords() {
        return this.nWords;
    }

    public void setIgnoredWords() throws IOException {
        this.ignoredWords.addAll(Files.readAllLines(Path.of(this.ignoredPath), Charset.defaultCharset()));
    }
}
