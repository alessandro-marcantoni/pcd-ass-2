package controller.tasks;

import commons.Pair;
import commons.Parameter;
import model.monitors.OccurrencesBuffer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Void> {

    private final List<String> text;
    private final Map<String, Integer> localOccurrences = new HashMap<>();

    public CountTask(String[] text) {
        this.text = Arrays.asList(text);
    }

    @Override
    protected Void compute() {
        this.text.stream()
                .filter(w -> !Parameter.INSTANCE.getIgnoredWords().contains(w))
                .forEach(w -> {
                    if (this.localOccurrences.containsKey(w)) {
                        this.localOccurrences.replace(w, this.localOccurrences.get(w) + 1);
                    } else {
                        this.localOccurrences.put(w, 1);
                    }
                });
        OccurrencesBuffer.INSTANCE.update(Pair.of(this.localOccurrences, this.text.size()));
        this.localOccurrences.clear();
        return null;
    }
}
