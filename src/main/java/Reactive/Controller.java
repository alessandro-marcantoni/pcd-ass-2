package Reactive;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.subjects.PublishSubject;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Controller {

    private Disposable disposable;
    private final PublishSubject<Parameter> stream;
    private final GUI gui;
    private StopFlag stopFlag;

    public Controller(PublishSubject<Parameter> stream, GUI gui) {
        this.gui = gui;
        this.stream = stream;
        this.setupStream();
    }

    private void setupStream() {
        this.disposable = stream.subscribe(p -> {
            p.setIgnoredWords();
            OccurrencesBuffer.INSTANCE.clear();
            Flowable.fromIterable(p.getFilesInDirectory())
                    .subscribeOn(Schedulers.computation())
                    .map(PDDocument::load)
                    .map(d -> {
                        PDFTextStripper stripper = new PDFTextStripper();
                        stripper.setSortByPosition(true);
                        stripper.setStartPage(1);
                        stripper.setEndPage(d.getNumberOfPages());
                        return stripper.getText(d)
                                .toLowerCase()
                                .replaceAll("\\p{Punct}|\\d", "")
                                .split("\\s+");
                    })
                    .doOnNext(t -> {
                        final Map<String, Integer> localOccurrences = new HashMap<>();
                        Arrays.stream(t).filter(w -> !p.getIgnoredWords().contains(w))
                                .forEach(w -> {
                                    if (localOccurrences.containsKey(w)) {
                                        localOccurrences.replace(w, localOccurrences.get(w) + 1);
                                    } else {
                                        localOccurrences.put(w, 1);
                                    }
                                });
                        OccurrencesBuffer.INSTANCE.update(Pair.of(localOccurrences, t.length));
                        System.out.println(Thread.currentThread().getName());
                    })
                    .subscribe();
        });
    }

    public void notifyStop() {
        this.disposable.dispose();
        this.stopFlag.stop();
        this.setupStream();
    }

    public void notifyStarted(Parameter parameter) {
        this.stopFlag = new StopFlag();
        new Viewer(this.gui, this.stopFlag, parameter).start();
    }

}
