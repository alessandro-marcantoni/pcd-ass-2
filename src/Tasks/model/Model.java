package Tasks.model;

import Tasks.commons.ModelObserver;
import Tasks.commons.Pair;
import Tasks.commons.WorkerParameter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Model {

    private final List<ModelObserver> observers = new ArrayList<>();
    private final Map<String, Integer> occurrences = new HashMap<>();
    private WorkerParameter parameter;
    private int wordsCounted;

    public void addObserver(final ModelObserver observer) {
        this.observers.add(observer);
    }

    public synchronized void init(final WorkerParameter parameter) {
        this.occurrences.clear();
        this.wordsCounted = 0;
        this.parameter = parameter;
    }

    public synchronized void update(final Pair<Map<String,Integer>,Integer> occ) {
        this.wordsCounted += occ.getSecond();
        occ.getFirst().keySet().forEach(k -> {
            if (this.occurrences.containsKey(k)) {
                this.occurrences.replace(k, this.occurrences.get(k) + occ.getFirst().get(k));
            } else {
                this.occurrences.put(k, occ.getFirst().get(k));
            }
        });
        this.notifyObservers();
    }

    private void notifyObservers() {
        this.observers.forEach(o -> o.modelUpdated(this.getOccurrences()));
    }

    private Pair<Map<String, Integer>, Integer> getOccurrences() {
        return Pair.of(
                this.occurrences.keySet().stream()
                        .sorted((a,b) -> this.occurrences.get(b) - this.occurrences.get(a))
                        .limit(this.parameter.getNWords())
                        .collect(Collectors.toMap(k -> k, this.occurrences::get)),
                this.wordsCounted
        );
    }

}
