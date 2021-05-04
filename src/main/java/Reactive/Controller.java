package Reactive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Disposable disposable;
    private final GUI gui;

    public Controller(GUI gui) {
        this.gui = gui;
    }

    public void notifyStop() {
        this.disposable.dispose();
    }

    public void notifyStarted(Parameter p) throws IOException {
        final Map<String, Integer> occurrences = new HashMap<>();
        Tasks.setParameter(p);
        final Flowable<Map<String, Integer>> flow =
                Flowable.fromIterable(p.getFilesInDirectory())
                        .flatMap(s ->
                                Flowable.just(s)
                                        .subscribeOn(Schedulers.computation())
                                        .map(Tasks::strip)
                                        .map(Tasks::count)
                        );
        this.disposable = flow.subscribe(map -> {
            map.forEach((s, c) ->  occurrences.merge(s, c, Integer::sum));
            final int nWords = occurrences.values().stream().reduce(Integer::sum).orElse(0);
            this.gui.modelUpdated(Pair.of(Tasks.getTop(occurrences), nWords));
        });
    }

}
