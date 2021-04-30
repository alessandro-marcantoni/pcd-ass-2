package EventLoop;

import io.vertx.core.Future;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class StationMonitor implements Runnable {

    final TrainSolution library;
    final GUI gui;

    private static final long FREQUENCY = 1000;

    public StationMonitor(final TrainSolution lib, GUI gui) {
        this.library = lib;
        this.gui = gui;
    }

    @Override
    public void run() {
        while(gui.isStationRunning()) {
            Future<List<Train>> future = library.getRealTimeStationInfo(gui.getStationCode());
            future.onSuccess(trains -> {
                gui.getStationArea().setText(trains.stream()
                    .map(t ->
                        "Numero: " + t.getNumber() + "\n" +
                        "Tipo: " + t.getCategory() + "\n" +
                        "Stazione di Origine: " + t.getOrigin() + "\n" +
                        "Stazione di Destinazione: " + t.getDestination() + "\n" +
                        "Binario di Arrivo: " + t.getPlatformArrival() + "\n" +
                        "Orario di Arrivo: " + getTime(t.getArrivalTime()) + "\n" +
                        "Binario di Partenza: " + t.getPlatformDeparture() + "\n" +
                        "Orario di Partenza: " + getTime(t.getDepartureTime()) + "\n" +
                        "Ritardo: " + t.getDelay() + " minuti\n\n")
                .collect(Collectors.toList()).toString()
                        .replaceAll("\\[", "")
                        .replaceAll("\\]", "")
                        .replaceAll(",", ""));
            });
            try {
                Thread.sleep(FREQUENCY);
                //gui.getStationArea().setText("");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String getTime(Date date) {
        return (date.getHours() < 10 ? "0"+date.getHours() : date.getHours()) +":"+ (date.getMinutes() < 10 ? "0"+date.getMinutes() : date.getMinutes());
    }
}
