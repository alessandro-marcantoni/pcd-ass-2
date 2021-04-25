package commons;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Parameter implements CommonParameter {

    public static final Parameter INSTANCE = new Parameter();

	private String directory;
    private String ignoredPath;
    private int nWords;
    private final Set<String> ignoredWords = new HashSet<>();

    @Override
    public List<File> getFilesInDirectory() {
        final File dirFile = new File(this.directory);
        return Arrays.asList(Objects.requireNonNull(dirFile.listFiles()));
    }

    @Override
    public Set<String> getIgnoredWords() {
        return this.ignoredWords;
    }

    @Override
    public int getNWords() {
        return this.nWords;
    }

    @Override
    public void setIgnoredWords() throws IOException {
        this.ignoredWords.addAll(Files.readAllLines(Path.of(this.ignoredPath), Charset.defaultCharset()));
    }

    @Override
    public void setParameters(final String directory, final String path, final int nWords) {
        this.directory = directory;
        this.ignoredPath = path;
        this.nWords = nWords;
    }
}
