package Reactive;

import java.util.Map;
import java.util.stream.Collectors;

public class Viewer extends Thread {

    private final GUI gui;
    private final StopFlag stopFlag;
    private final Parameter parameter;

    public Viewer(GUI gui, StopFlag stopFlag, Parameter parameter) {
        this.gui = gui;
        this.stopFlag = stopFlag;
        this.parameter = parameter;
    }

    @Override
    public void run() {
        while (!this.stopFlag.isStopped()) {
            try {
                final Pair<Map<String, Integer>, Integer> pair = OccurrencesBuffer.INSTANCE.getOccurrences();
                this.gui.modelUpdated(Pair.of(pair.getFirst().keySet().stream()
                        .sorted((a,b) -> pair.getFirst().get(b) - pair.getFirst().get(a))
                        .limit(parameter.getNWords())
                        .collect(Collectors.toMap(k -> k, pair.getFirst()::get)), pair.getSecond()));
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        final Pair<Map<String, Integer>, Integer> pair = OccurrencesBuffer.INSTANCE.getOccurrences();
        this.gui.modelUpdated(Pair.of(pair.getFirst().keySet().stream()
                .sorted((a,b) -> pair.getFirst().get(b) - pair.getFirst().get(a))
                .limit(parameter.getNWords())
                .collect(Collectors.toMap(k -> k, pair.getFirst()::get)), pair.getSecond()));
    }
}
