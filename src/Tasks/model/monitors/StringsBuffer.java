package Tasks.model.monitors;

import java.util.*;

public class StringsBuffer {

    public static final StringBuffer INSTANCE = new StringBuffer();

    private StringsBuffer() {}

    private final List<String> words = new ArrayList<>();
    private final List<String> subList = new ArrayList<>();
    private int wordsPerThread = 0;

    public synchronized void addStrings(final String[] words) {
        this.words.addAll(Arrays.asList(words));
        this.wordsPerThread = this.words.size() / 8 + 1;
    }

    public synchronized Optional<List<String>> getStrings() {
        if (this.words.isEmpty()) {
            return Optional.empty();
        }
        this.subList.clear();
        this.subList.addAll(List.copyOf(this.words.subList(0, Math.min(this.wordsPerThread, this.words.size()))));
        this.words.subList(0, Math.min(this.wordsPerThread, this.words.size())).clear();
        return Optional.of(List.copyOf(this.subList));
    }
}
