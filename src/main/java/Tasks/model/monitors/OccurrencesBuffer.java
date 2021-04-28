package Tasks.model.monitors;

import Tasks.commons.Pair;
import Tasks.commons.Parameter;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class OccurrencesBuffer {

    public static final OccurrencesBuffer INSTANCE = new OccurrencesBuffer();

    private final Map<String, Integer> occurrences = new HashMap<>();
    private int wordsCounted;

    private OccurrencesBuffer() {}

    public synchronized void update(final Pair<Map<String,Integer>,Integer> occ) {
        this.wordsCounted += occ.getSecond();
        occ.getFirst().keySet().forEach(k -> {
            if (this.occurrences.containsKey(k)) {
                this.occurrences.replace(k, this.occurrences.get(k) + occ.getFirst().get(k));
            } else {
                this.occurrences.put(k, occ.getFirst().get(k));
            }
        });
    }

    public synchronized Pair<Map<String, Integer>, Integer> getOccurrences() {
        return Pair.of(
                this.occurrences.keySet().stream()
                        .sorted((a,b) -> this.occurrences.get(b) - this.occurrences.get(a))
                        .limit(Parameter.INSTANCE.getNWords())
                        .collect(Collectors.toMap(k -> k, this.occurrences::get)),
                this.wordsCounted
        );
    }

    public synchronized void clear() {
        this.occurrences.clear();
        this.wordsCounted = 0;
    }

}
