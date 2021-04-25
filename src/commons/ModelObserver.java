package commons;

import java.util.Map;

public interface ModelObserver {

    void modelUpdated(Pair<Map<String,Integer>,Integer> occurrences);

}
